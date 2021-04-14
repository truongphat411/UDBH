package com.example.appbanhang;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class donMuaViewPaperAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> mFragmentList;
    ArrayList<String> mFragmentTittleList;
    private String[] tabTitles = new String[]{"Tạo đơn", "Chờ xác nhận","Đang giao","Đã giao","Đã Hủy"};

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
    public donMuaViewPaperAdapter(@NonNull FragmentManager fm) {
        super(fm);
        mFragmentList = new ArrayList<>();
        mFragmentTittleList = new ArrayList<>();
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentTaoDon();
            case 1:
                return new FragmentChoXacNhan();
            case 2:
                return new FragmentDangGiao();
            case 3:
                return new FragmentDaGiao();
            case 4:
                return new FragmentDaHuy();
            default:
                throw new RuntimeException("Invalid tab position");
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    public void addFragment(Fragment fragment,String title){
        mFragmentList.add(fragment);
        mFragmentTittleList.add(title);
    }
}
