package com.example.appbanhang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class Fragment_AdminChoXacNhan extends Fragment {
    public static ArrayList<HoaDon> listCXN;
    RecyclerView recyclerView;
    DatabaseReference reference;
    RecyclerViewDonHang_admin adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adminchoxacnhan,container,false);
        recyclerView = view.findViewById(R.id.listChoXacNhan);

        return view;
    }
    private void DataFromFirebaseListener() {
        listCXN = new ArrayList<>();
        Query query = reference.orderByChild("trangthai").equalTo("Chờ Xác Nhận");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String key = ds.child("id").getValue(String.class);
                    String idUser = ds.child("idUser").getValue(String.class);
                    String ngayTaoDon = ds.child("ngaytaodon").getValue(String.class);
                    String tenUser = ds.child("hoten").getValue(String.class);
                    String sodienthoai = ds.child("sodienthoai").getValue(String.class);
                    int tongtien = ds.child("tongtien").getValue(Integer.class);
                    int laisuat = ds.child("laisuat").getValue(Integer.class);
                    String trangthai = ds.child("trangthai").getValue(String.class);
                    String diachi = ds.child("diachi").getValue(String.class);
                        HoaDon hd = new HoaDon(key,tongtien,ngayTaoDon,"",tenUser,sodienthoai,diachi,trangthai,idUser,"",laisuat);
                        listCXN.add(hd);
                    }
                    adapter.notifyDataSetChanged();
                    if(listCXN.size() == 0){
                    donHangTrong fragment = new donHangTrong();
                    FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameADMINchoxacnhan, fragment);
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
        adapter = new RecyclerViewDonHang_admin(getActivity() , listCXN);
        recyclerView.setAdapter(adapter);
    }
}
