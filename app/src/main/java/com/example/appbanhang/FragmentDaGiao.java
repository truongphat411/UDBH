package com.example.appbanhang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.appbanhang.R;

public class FragmentDaGiao extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentdagiao,container,false);
        if (savedInstanceState == null){
            FragmentChuaCoDonMua fragment = new FragmentChuaCoDonMua();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.framedagiao,fragment);
            fragmentTransaction.commit();
        }
        return view;
    }
}
