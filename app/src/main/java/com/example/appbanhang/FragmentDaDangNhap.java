package com.example.appbanhang;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class FragmentDaDangNhap extends Fragment {
    TextView txthoten,txtsodienthoai,txtXemTT;
    RelativeLayout rlttcn;
    Button btnDangXuat;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentdadangnhap,container,false);
        rlttcn = view.findViewById(R.id.rlttcn);
        txthoten = view.findViewById(R.id.txthoten);
        txtsodienthoai = view.findViewById(R.id.txtsodienthoai);
        btnDangXuat = view.findViewById(R.id.btnDangXuat);
        txtXemTT = view.findViewById(R.id.txtXemTT);
        txthoten.setText(MainActivity.hoten);
        txtsodienthoai.setText(MainActivity.sodienthoai);
        rlttcn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),FormTTCN.class);
                startActivity(intent);
            }
        });
        txtXemTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),tienDoGiaoHang.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
