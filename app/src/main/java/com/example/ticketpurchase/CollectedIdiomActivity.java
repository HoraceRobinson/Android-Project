package com.example.ticketpurchase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class CollectedIdiomActivity extends AppCompatActivity {

    private ArrayList<String> idioms;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        fragmentManager = getSupportFragmentManager();
        idioms = new ArrayList<>();
        idioms.add("一夫当关");
        idioms.add("万夫莫开");
        idioms.add("过五关斩六将");
        idioms.add("杀人如麻");
        IdiomListFragment listFragment = new IdiomListFragment(fragmentManager, idioms);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,listFragment);
        fragmentTransaction.commit();

    }
}