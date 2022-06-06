package com.example.ticketpurchase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ticketpurchase.room.DBEngine;
import com.example.ticketpurchase.room.Idiom;

import java.util.ArrayList;
import java.util.List;

public class CollectedIdiomActivity extends AppCompatActivity {

    private DBEngine dbEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        dbEngine = new DBEngine(this);
        dbEngine.getAllIdioms(this);
    }


    public void add(View view) {
        Idiom idiom1 = new Idiom("一夫当关","yifudangguan","一个人在关","出处","例句");
        Idiom idiom2 = new Idiom("万夫莫开","wanfumokai","万个人卜凯","出处","例句");
        Idiom idiom3 = new Idiom("杀人如麻","sharenruma","杀人像嘛","出处","例句");
        dbEngine.insertIdioms(idiom1,idiom2,idiom3);
    }
}