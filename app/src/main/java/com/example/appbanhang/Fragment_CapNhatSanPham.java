package com.example.appbanhang;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.models.SanPham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Fragment_CapNhatSanPham extends Fragment {
    RecyclerView recyclerView;
    Button btnthemSP;
    DatabaseReference reference;
    ArrayList<SanPham> list;
    RecyclerView_CapnhatSanPham adapter;
    public static boolean isCapnhat = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_sanpham,container,false);
         recyclerView = view.findViewById(R.id.recyclerSanPham);
         btnthemSP = view.findViewById(R.id.btnthemSP);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        loadData();
        btnthemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Activity_ThemSP.class);
                startActivity(intent);
            }
        });
         return view;
    }
    private void loadData(){
        list = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference().child("sanpham");
        reference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    String key = ds.getKey();
                    String hinhsp = ds.child("hinhSP").getValue(String.class);
                    String tensp = ds.child("tenSP").getValue(String.class);
                    int giasp = ds.child("giaSP").getValue(Integer.class);
                    String tenth = ds.child("tenTH").getValue(String.class);
                    String motasp = ds.child("motaSP").getValue(String.class);
                    String idTH = ds.child("idTH").getValue(String.class);
                    int soluongKho = ds.child("soluongKho").getValue(Integer.class);
                    AtomicBoolean isSanPham = new AtomicBoolean();

                    list.forEach(sanPham -> {
                        if(sanPham.getIdSP().equals(key)){
                            isSanPham.set(true);
                        }
                    });

                    if(!isSanPham.get()){
                        SanPham sp = new SanPham(key, tensp, hinhsp, giasp, tenth, motasp, idTH,soluongKho);
                        list.add(sp);
                    }
                }
                adapter = new RecyclerView_CapnhatSanPham(getActivity(),list);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}
