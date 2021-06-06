package com.example.appbanhang;

import android.os.Bundle;
import android.util.Log;
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
import java.util.concurrent.atomic.AtomicBoolean;

public class Fragment_AdminDaHuy extends Fragment {
    public static ArrayList<HoaDon> listDH;
    RecyclerView recyclerView;
    DatabaseReference reference;
    RecyclerViewDonHang_admin adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admindahuy,container,false);
        recyclerView = view.findViewById(R.id.listDaHuy);

        return view;
    }
    private void DataFromFirebaseListener() {
        listDH = new ArrayList<HoaDon>();
        Query query = reference.orderByChild("trangthai").equalTo("Đã Hủy");
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
                    String trangthai = ds.child("trangthai").getValue(String.class);
                    String diachi = ds.child("diachi").getValue(String.class);
                    int laisuat = ds.child("laisuat").getValue(Integer.class);
                        HoaDon hd = new HoaDon(key, tongtien, ngayTaoDon, "", tenUser, sodienthoai, diachi, trangthai, idUser,"",laisuat);
                        listDH.add(hd);
                    }
                    adapter.notifyDataSetChanged();
                if (listDH.size() == 0) {
                    donHangTrong fragment = new donHangTrong();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameADMINdahuy, fragment);
                    fragmentTransaction.commit();
                }
                Log.d("MTP", "onDataChange: 9");
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
        adapter = new RecyclerViewDonHang_admin(getActivity() , listDH);
        recyclerView.setAdapter(adapter);
    }
}
