package com.example.appbanhang.models;

public class SanPham {


    public String idSP;

    public String getIdSP() {
        return idSP;
    }

    public void setIdSP(String idSP) {
        this.idSP = idSP;
    }

    public String tenSP;
    public String hinhSP;
    public int giaSP;
    public boolean isYeuThich;

    public int getSoluongKho() {
        return soluongKho;
    }

    public void setSoluongKho(int soluongKho) {
        this.soluongKho = soluongKho;
    }

    public int soluongKho;
    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int soluong = 0;

    public boolean isYeuThich() {
        return isYeuThich;
    }
    public void setYeuThich(boolean yeuThich) {
        isYeuThich = yeuThich;
    }
    public String tenTH;
    public String motaSP;
    public String idTH;

    public String getIdTH() {
        return idTH;
    }

    public void setIdTH(String idTH) {
        this.idTH = idTH;
    }

    public String getMotaSP() {
        return motaSP;
    }

    public void setMotaSP(String motaSP) {
        this.motaSP = motaSP;
    }

    public SanPham(String idSP, String tenSP, String hinhSP, int giaSP, String tenTH, String motaSP,String idTH,int soluongKho) {
        this.idSP = idSP;
        this.tenSP = tenSP;
        this.hinhSP = hinhSP;
        this.giaSP = giaSP;
        this.tenTH = tenTH;
        this.motaSP = motaSP;
        this.idTH = idTH;
        this.soluongKho = soluongKho;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getHinhSP() {
        return hinhSP;
    }

    public void setHinhSP(String hinhSP) {
        this.hinhSP = hinhSP;
    }

    public int getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(int giaSP) {
        this.giaSP = giaSP;
    }

    public String getTenTH() {
        return tenTH;
    }

    public void setTenTH(String tenTH) {
        this.tenTH = tenTH;
    }
}
