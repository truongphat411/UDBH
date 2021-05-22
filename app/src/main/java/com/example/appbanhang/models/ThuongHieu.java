package com.example.appbanhang.models;

public class ThuongHieu {
    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTenTH() {
        return TenTH;
    }

    public void setTenTH(String tenTH) {
        TenTH = tenTH;
    }

    public String getHinhTH() {
        return HinhTH;
    }

    public void setHinhTH(String hinhTH) {
        HinhTH = hinhTH;
    }

    public String ID;
    public String TenTH;
    public String HinhTH;

    public ThuongHieu(String ID, String tenTH, String hinhTH) {
        this.ID = ID;
        TenTH = tenTH;
        HinhTH = hinhTH;
    }
}
