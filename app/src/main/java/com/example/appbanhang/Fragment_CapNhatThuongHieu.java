package com.example.appbanhang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class Fragment_CapNhatThuongHieu extends Fragment {
    RecyclerView recyclerView;
    Button btnthemTH;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sanpham,container,false);
        recyclerView = view.findViewById(R.id.recyclerThuongHieu);
        btnthemTH = view.findViewById(R.id.btnthemTH);
        return view;
    }
}
