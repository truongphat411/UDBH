package com.example.appbanhang;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.models.HoaDon;
import java.util.ArrayList;


public class RecyclerViewDonHang extends RecyclerView.Adapter<RecyclerViewDonHang.ViewHolder> {
     private Context mContext;
     private ArrayList<HoaDon> hoaDonArrayList;
    public RecyclerViewDonHang(Context mContext, ArrayList<HoaDon> hoaDonArrayList) {
        this.mContext = mContext;
        this.hoaDonArrayList = hoaDonArrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemdonhang,parent,false);
        return new ViewHolder(view);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtmadonhang.setText("Mã đơn hàng: "+hoaDonArrayList.get(position).getId());
        holder.txtngaydukien.setText("Hàng sẽ được xác nhận trước: "+hoaDonArrayList.get(position).getNgaytaodon());
        holder.txttongtien.setText(String.valueOf(hoaDonArrayList.get(position).getTongtien())+"đ");
    }

    @Override
    public int getItemCount() {
        return hoaDonArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtmadonhang,txttongtien,txtngaydukien;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmadonhang = itemView.findViewById(R.id.txtmadonhang);
            txttongtien = itemView.findViewById(R.id.txttongtiendonhang);
            txtngaydukien = itemView.findViewById(R.id.txtngaydk);
        }
    }
}