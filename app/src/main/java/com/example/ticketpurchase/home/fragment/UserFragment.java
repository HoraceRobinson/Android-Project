package com.example.ticketpurchase.home.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ticketpurchase.R;
import com.example.ticketpurchase.idiom.StarItemActivity;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

public class UserFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_user, container, false);
        SuperTextView starList = view.findViewById(R.id.star_idiom_list);
        starList.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(SuperTextView superTextView) {
                startActivity(new Intent(getActivity(), StarItemActivity.class));
            }
        });
        SuperTextView username = view.findViewById(R.id.username);
        SharedPreferences sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        username.setCenterString(sp.getString("username", null));
        return view;
    }
}
