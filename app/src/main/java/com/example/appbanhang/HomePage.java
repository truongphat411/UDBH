package com.example.appbanhang;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.appbanhang.models.SanPham;
import com.example.appbanhang.models.ThuongHieu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class HomePage extends Fragment {
    TextView txtSearch;
    GridView gridView;
    ArrayList<ThuongHieu> thuongHieuArrayList = new ArrayList<>();
    DatabaseReference reference;
    CustomAdapter customAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container , false);
        gridView = view.findViewById(R.id.grid_view);
        txtSearch = view.findViewById(R.id.txtSearch);
        DataFromFirebaseListener();
        customAdapter = new CustomAdapter(getActivity(), thuongHieuArrayList);
        gridView.setAdapter(customAdapter);
        txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SeachView.class);
                startActivity(intent);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ThuongHieu thuongHieuItem = thuongHieuArrayList.get(position);
                Intent intent = new Intent(getActivity(), SanPhamPage.class);
                intent.putExtra("id", thuongHieuItem.getID());
                intent.putExtra("hinh",thuongHieuItem.getHinhTH());
                startActivity(intent);
            }

        });
        return view;
    }
    public void DataFromFirebaseListener() {
        reference = FirebaseDatabase.getInstance().getReference().child("thuonghieu");
        reference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    String key = ds.getKey();
                    String hinhTH = ds.child("hinh").getValue(String.class);
                    String tenTH = ds.child("tenTH").getValue(String.class);
                    AtomicBoolean isDaTonTai = new AtomicBoolean(false);
                    thuongHieuArrayList.forEach(thuongHieu -> {
                        if((thuongHieu.getID()).equals(key)){
                            isDaTonTai.set(true);
                        }
                    });
                    if(!isDaTonTai.get()){
                        ThuongHieu thuongHieu = new ThuongHieu(key , tenTH, hinhTH);
                        thuongHieuArrayList.add(thuongHieu);
                    }
                }
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // ...
            }
        });
    }
}
