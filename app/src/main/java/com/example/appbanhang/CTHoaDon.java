package com.example.appbanhang;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.models.SanPham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class CTHoaDon extends AppCompatActivity {
    ImageButton imgBack;
    TextView txtTenNhanHang,txtSoDienThoaiNhanHang,txtDiaChiNhanHang;
    RecyclerView recyclerChiTietDonHang;
    Button btnHuyDonHang;
    DatabaseReference referenceCTHD,referenceSP;
    Recycler_ChiTietDonHang adapter;
    ArrayList<SanPham> list;
    String idHD;
    int tongtien;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitietdonhang);
        anhxa();
        Intent intent = getIntent();
        idHD = intent.getStringExtra("idHD");
        tongtien = intent.getIntExtra("tongtien",0);
        txtTenNhanHang.setText(intent.getStringExtra("hoten"));
        txtSoDienThoaiNhanHang.setText(intent.getStringExtra("sodienthoai"));
        txtDiaChiNhanHang.setText(intent.getStringExtra("diachi"));
        referenceCTHD = FirebaseDatabase.getInstance().getReference().child("chitiethoadon");
        referenceSP = FirebaseDatabase.getInstance().getReference().child("sanpham");
        DataFromFirebaseListener();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CTHoaDon.this);
        recyclerChiTietDonHang.setLayoutManager(linearLayoutManager);
        adapter = new Recycler_ChiTietDonHang(CTHoaDon.this , list);
        recyclerChiTietDonHang.setAdapter(adapter);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void DataFromFirebaseListener() {
        list = new ArrayList<SanPham>();
        referenceCTHD.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    String key = ds.getKey();
                    int idSP = snapshot.child(key).child("idSP").getValue(Integer.class);
                    String idHDFromDatabase = snapshot.child(key).child("idHD").getValue(String.class);
                    int soluong = snapshot.child(key).child("soluong").getValue(Integer.class);

                    AtomicBoolean isHoaDon = new AtomicBoolean();
                    if(idHD.equals(idHDFromDatabase)){
                        isHoaDon.set(true);
                    }
                    referenceSP.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds : snapshot.getChildren()) {
                                String key = ds.getKey();
                                String hinhsp = ds.child("hinhSP").getValue(String.class);
                                String tensp = ds.child("tenSP").getValue(String.class);
                                int giasp = ds.child("giaSP").getValue(Integer.class);
                                String idTH = ds.child("idTH").getValue(String.class);
                                String motaSP = ds.child("motaSP").getValue(String.class);
                                int soluongKho = ds.child("soluongKho").getValue(Integer.class);
                                AtomicBoolean isSanPham = new AtomicBoolean();
                                if(key.equals(idSP)){
                                    isSanPham.set(true);
                                }
                                if(isHoaDon.get() && isSanPham.get()){
                                    SanPham sanPham = new SanPham(key,tensp,hinhsp,soluong*giasp,"",motaSP,idTH,soluongKho);
                                    list.add(sanPham);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void anhxa(){
        imgBack = findViewById(R.id.imgb);
        txtTenNhanHang = findViewById(R.id.txtTenNhanHang);
        txtSoDienThoaiNhanHang = findViewById(R.id.txtSoDienThoaiNhanHang);
        txtDiaChiNhanHang = findViewById(R.id.txtDiaChiNhanHang);
        recyclerChiTietDonHang = findViewById(R.id.RecyclerChiTietDonHang);
        btnHuyDonHang = findViewById(R.id.btnHuyDonHang);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.aqua));
        }
    }
}
