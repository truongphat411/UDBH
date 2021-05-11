package com.example.appbanhang;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class activity_capnhatsanpham extends AppCompatActivity {
    EditText edttenSP,edtgiaSP,edtmotaSP,edtmathuonghieu,edtmadanhmuc,edtsoluongKho;
    ImageView imvhinhSP;
    Button btnthemSP,btnxoaSP;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietcapnhat);
        anhxa();
        loadData();
    }
        private void anhxa() {
        edttenSP = findViewById(R.id.edttenSP);
        edtgiaSP = findViewById(R.id.edtgiaSP);
        edtmotaSP = findViewById(R.id.edtmotaSP);
        edtmathuonghieu = findViewById(R.id.edtmathuonghieu);
        edtmadanhmuc = findViewById(R.id.edtmadanhmuc);
        edtsoluongKho =findViewById(R.id.edtsoluongkho);
        imvhinhSP = findViewById(R.id.imvhinhSP);
        btnthemSP = findViewById(R.id.btnthemSP);
        btnxoaSP = findViewById(R.id.btnxoaSP);
    }
    private void loadData(){
        Intent intent = getIntent();
        edttenSP.setText(intent.getStringExtra("tenSP"));
        intent.getStringExtra("idSP");
        edtgiaSP.setText(String.valueOf(intent.getIntExtra("giaSP",0)));
        edtmotaSP.setText(intent.getStringExtra("motaSP"));
        edtsoluongKho.setText(String.valueOf(intent.getIntExtra("soluongKho",0)));
        Uri myUri = Uri.parse(intent.getStringExtra("hinhSP"));
        Picasso.with(activity_capnhatsanpham.this).load(myUri).placeholder(R.drawable.image).into(imvhinhSP);
        edtmathuonghieu.setText(String.valueOf(intent.getIntExtra("idTH",0)));
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
