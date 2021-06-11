package com.example.appbanhang;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.R;
import com.example.appbanhang.models.HoaDon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class FragmentDaGiao extends Fragment {
    public ArrayList<HoaDon> listDG;
    RecyclerView recyclerView;
    DatabaseReference reference;
    RecyclerViewDonHang adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentdagiao,container,false);
        recyclerView = view.findViewById(R.id.listDaGiao);
        return view;
    }
    private void DataFromFirebaseListener() {
        listDG = new ArrayList<>();
        Query query = reference.orderByChild("trangthai").equalTo("Đã Giao");
        query.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String key = ds.child("id").getValue(String.class);
                    String idUser = ds.child("idUser").getValue(String.class);
                    String ngayTaoDon = ds.child("ngaytaodon").getValue(String.class);
                    String tenUser = ds.child("hoten").getValue(String.class);
                    String sodienthoai = ds.child("sodienthoai").getValue(String.class);
                    int tongtien = ds.child("tongtien").getValue(Integer.class);
                    String trangthai = ds.child("trangthai").getValue(String.class);
                    String diachi = ds.child("diachi").getValue(String.class);
                    int laisuat = ds.child("laisuat").getValue(Integer.class);
                    String lido = ds.child("lido").getValue(String.class);
                    AtomicBoolean isTaiKhoan = new AtomicBoolean();
                    AtomicBoolean isDatontai = new AtomicBoolean();
                    listDG.forEach(hoaDon -> {
                        if(hoaDon.getId().equals(key)){
                            isDatontai.set(true);
                        }
                    });
                    if (idUser.equals(MainActivity.id)) {
                        isTaiKhoan.set(true);
                    }
                    if (isTaiKhoan.get() && !isDatontai.get()) {
                        HoaDon hd = new HoaDon(key, tongtien, ngayTaoDon, "", tenUser, sodienthoai, diachi, trangthai, idUser,lido,laisuat);
                        listDG.add(hd);
                    }
                    adapter.notifyDataSetChanged();
                }
                if(listDG.size() == 0){
                    donHangTrong fragment = new donHangTrong();
                    FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameDaGiao, fragment);
                    fragmentTransaction.commit();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        reference = FirebaseDatabase.getInstance().getReference().child("hoadon");
        DataFromFirebaseListener();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerViewDonHang(getActivity() , listDG);
        recyclerView.setAdapter(adapter);
    }
}
