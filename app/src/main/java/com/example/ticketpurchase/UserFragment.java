package com.example.ticketpurchase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
        return view;
    }
}
