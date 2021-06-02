package com.example.appbanhang;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanhang.models.HoaDon;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThongKe extends AppCompatActivity {
    BarChart barChart;
    DatabaseReference reference;
    ArrayList<HoaDon> list = new ArrayList<>();
    private int i;
    private ArrayList<BarEntry> visitors = new ArrayList<>();
    private ArrayList<String> days = new ArrayList<>();
    private String formattedDate1,formattedDate2;
    private int tongtien;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thongke);
        barChart = findViewById(R.id.barChart);
        readData();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setBarChart() {
/*        ArrayList<BarEntry> visitors = new ArrayList<>();
        ArrayList<String> days = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference().child("hoadon");*/
/*        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    String ngayhoanthanh = ds.child("ngayhoanthanh").getValue(String.class);
                    int tongtien = ds.child("tongtien").getValue(Integer.class);
                    for(int i = 0;i<=7;i++){
                        final LocalDate date = LocalDate.now();
                        final LocalDate dateMinus7Days = date.minusDays(i);
                        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM");
                        final String formattedDate1 = dateMinus7Days.format(formatter1);
                        final String formattedDate2 = dateMinus7Days.format(formatter2);
                        if(ngayhoanthanh.equals(formattedDate1)){
                            visitors.add(new BarEntry(i,tongtien));

                            BarDataSet barDataSet = new BarDataSet(visitors,"Doanh Thu");
                            barDataSet.setValueTextColor(Color.BLACK);
                            barDataSet.setValueTextSize(16f);

                            BarData barData = new BarData(barDataSet);
                            barChart.setData(barData);


                            days.add(formattedDate2);

                            XAxis xAxis = barChart.getXAxis();
                            xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
                            xAxis.setCenterAxisLabels(false);
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                            xAxis.setGranularity(1);
                            xAxis.setGranularityEnabled(true);


                            barData.setBarWidth(0.1f);

                            barChart.setFitBars(true);
                            barChart.getDescription().setText("");
                            barChart.animateY(2000);
                        }


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        for(i = 6;i>=0;i--) {
            final LocalDate date = LocalDate.now();
            final LocalDate dateMinus7Days = date.minusDays(i);
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM");
            formattedDate1 = dateMinus7Days.format(formatter1);
            formattedDate2 = dateMinus7Days.format(formatter2);
            list.forEach(hoaDon -> {
                if(hoaDon.getNgayhoanthanh().equals(formattedDate1)){
                    if(hoaDon.getTongtien() > 0){
                        visitors.add(new BarEntry(i, hoaDon.getTongtien()));
                    }else {
                        visitors.add(new BarEntry(i, 0));
                    }
                }
            });
            BarDataSet barDataSet = new BarDataSet(visitors,"Doanh Thu");
            barDataSet.setValueTextColor(Color.BLACK);
            barDataSet.setValueTextSize(16f);

            BarData barData = new BarData(barDataSet);
            barChart.setData(barData);

            days.add(formattedDate2);

            XAxis xAxis = barChart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
            xAxis.setCenterAxisLabels(false);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1);
            xAxis.setGranularityEnabled(true);

            barData.setBarWidth(0.1f);

            barChart.setFitBars(true);
            barChart.getDescription().setText("");
            barChart.animateY(2000);
        }
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
    private void readData(){
        reference = FirebaseDatabase.getInstance().getReference().child("hoadon");
        reference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        int tongtien = ds.child("tongtien").getValue(Integer.class);
                        String ngayhoanthanh = ds.child("ngayhoanthanh").getValue(String.class);
                        HoaDon hoaDon = new HoaDon("", tongtien, "", ngayhoanthanh, "", "", "", "", "");
                        list.add(hoaDon);
                    }
                    if (list.size() > 0) {
                        for (i = 6; i > 0; i--) {
                            AtomicBoolean isCheckDate = new AtomicBoolean();
                            final LocalDate date = LocalDate.now();
                            final LocalDate dateMinus7Days = date.minusDays(i);
                            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM");
                            formattedDate1 = dateMinus7Days.format(formatter1);
                            formattedDate2 = dateMinus7Days.format(formatter2);
                            list.forEach(hoaDon -> {
                                if (hoaDon.getNgayhoanthanh().equals(formattedDate1)) {
                                    tongtien += hoaDon.getTongtien();
                                    isCheckDate.set(true);
                                }
                            });
                            if(isCheckDate.get()){
                                visitors.add(new BarEntry(i, tongtien));
                            }else {
                                visitors.add(new BarEntry(i, 0));
                            }
                            tongtien = 0;
                            BarDataSet barDataSet = new BarDataSet(visitors, "Doanh Thu");
                            barDataSet.setValueTextColor(Color.BLACK);
                            barDataSet.setValueTextSize(16f);

                            BarData barData = new BarData(barDataSet);
                            barChart.setData(barData);

                            days.add(formattedDate2);

                            XAxis xAxis = barChart.getXAxis();
                            xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
                            xAxis.setCenterAxisLabels(false);
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                            xAxis.setGranularity(1);
                            xAxis.setGranularityEnabled(true);

                            barData.setBarWidth(0.1f);

                            barChart.setFitBars(true);
                            barChart.getDescription().setText("");
                            barChart.animateY(2000);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
