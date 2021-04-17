package com.example.appbanhang;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.appbanhang.models.LichSuTruyCap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Constants;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class FragmentDangNhap extends Fragment {
    EditText edtsodienthoai,edtmatkhau;
    Button btnDangNhap;
    public static final  String SHARED_PREFS = "sharedPrefs";
    DatabaseReference referenceLSTC;
    LichSuTruyCap lichSuTruyCap;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_dang_nhap,container,false);
        edtsodienthoai = view.findViewById(R.id.edtsodienthoai);
        edtmatkhau = view.findViewById(R.id.edtmatkhau);
        btnDangNhap = view.findViewById(R.id.btnDangNhap);
        referenceLSTC = FirebaseDatabase.getInstance().getReference().child("lichsutruycap");
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateTenDangNhap() | !validateMatKhau()){
                    return;
                }else {
                    isUser();
                }
            }
        });
        return view;
    }

    private Boolean validateTenDangNhap (){
        String val = edtsodienthoai.getText().toString();

        if(val.isEmpty()){
            edtsodienthoai.setError("Vui lòng nhập tên đăng nhập");
            return false;
        }else {
            edtsodienthoai.setError(null);
            return true;
        }
    }
    private Boolean validateMatKhau (){
        String val = edtsodienthoai.getText().toString();
        if(val.isEmpty()){
            edtmatkhau.setError("Vui lòng nhập mật khẩu");
            return false;
        }else {
            edtmatkhau.setError(null);
            return true;
        }
    }
    public  void isUser(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("taikhoan");
        String phone = edtsodienthoai.getText().toString().trim();
        String password = edtmatkhau.getText().toString().trim();
        Query checkTenDangNhap = reference.orderByChild("sodienthoai").equalTo(phone);

        checkTenDangNhap.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for( DataSnapshot child : snapshot.getChildren() ) {
                    String childKey = child.getKey();
                    if(snapshot.child(childKey).exists()) {
                        edtsodienthoai.setError(null);
                        String passwordFromDB = snapshot.child(childKey).child("matkhau").getValue(String.class);
                        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), passwordFromDB);
                        if (result.verified) {
                            edtsodienthoai.setError(null);
                            String hotenFromDB = snapshot.child(childKey).child("hoten").getValue(String.class);
                            String sodienthoaiFromDB = snapshot.child(childKey).child("sodienthoai").getValue(String.class);
                            String key = snapshot.child(childKey).child("id").getValue(String.class);
                            String diachiFromDB = snapshot.child(childKey).child("diachi").getValue(String.class);
                            String ngaysinhFromDB = snapshot.child(childKey).child("ngaysinh").getValue(String.class);
                            String gioitinhFromDB = snapshot.child(childKey).child("gioitinh").getValue(String.class);
                            String tenLoaiFromDB = snapshot.child(childKey).child("tenLoai").getValue(String.class);
                            String ngaythamgiaFromDB = snapshot.child(childKey).child("ngaythamgia").getValue(String.class);
                            boolean hoatdong = snapshot.child(childKey).child("hoatdong").getValue(Boolean.class);
                            MainActivity.id = key;
                            MainActivity.dadangnhap = true;
                            MainActivity.hoten = hotenFromDB;
                            MainActivity.sodienthoai = sodienthoaiFromDB;
                            MainActivity.diachi = diachiFromDB;
                            MainActivity.ngaysinh = ngaysinhFromDB;
                            MainActivity.gioitinh = gioitinhFromDB;
                            MainActivity.tenLoai = tenLoaiFromDB;
                            MainActivity.ngaythamgia = ngaythamgiaFromDB;
                            saveData();
                            if(hoatdong == true){
                                String keyLSTC = referenceLSTC.push().getKey();
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                String ngaygio = sdf.format(calendar.getTime());
                                if(MainActivity.tenLoai.equals("client")) {
                                    lichSuTruyCap = new LichSuTruyCap(hotenFromDB, sodienthoaiFromDB, ngaygio, "Đăng Nhập");
                                    referenceLSTC.child(keyLSTC).setValue(lichSuTruyCap);
                                }
                                Toast.makeText(getContext(),"Đăng Nhập Thành Công",Toast.LENGTH_LONG).show();
                                getActivity().finish();
                            }

                            else {
                                AlertDialog alertDialog   = new AlertDialog.Builder(getActivity()).create();
                                alertDialog.setTitle("Thông báo");
                                alertDialog.setMessage("Tài khoản hiện đã tạm khóa.");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            }
                        }else {
                            edtmatkhau.setError("Wrong Password");
                            edtmatkhau.requestFocus();
                        }
                }else {
                        continue;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void saveData(){
        SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("hoten",MainActivity.hoten);
        editor.putString("sodienthoai",MainActivity.sodienthoai);
        editor.putString("diachi",MainActivity.diachi);
        editor.putString("ngaysinh",MainActivity.ngaysinh);
        editor.putString("gioitinh",MainActivity.gioitinh);
        editor.putString("ngaythamgia",MainActivity.ngaythamgia);
        editor.putString("tenloai",MainActivity.tenLoai);
        editor.putBoolean("daDangNhap",MainActivity.dadangnhap);
        editor.putString("id",MainActivity.id);
        editor.putBoolean("dadangnhap",MainActivity.dadangnhap);
//        // creating a new variable for gson.
//        Gson gson = new Gson();
//        // getting data from gson and storing it in a string.
//        String json = gson.toJson(MainActivity.listGH);
//        String json2 = gson.toJson(MainActivity.listYT);
//        // below line is to save data in shared
//        // prefs in the form of string.
//        editor.putString("listGH", json);
//        editor.putString("listYT", json2);
        editor.apply();
    }
}
