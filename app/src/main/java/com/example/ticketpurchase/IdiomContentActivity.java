package com.example.ticketpurchase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ticketpurchase.room.DBEngine;
import com.example.ticketpurchase.room.Idiom;

public class IdiomContentActivity extends AppCompatActivity {

    private DBEngine dbEngine;
    private Idiom idiom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idiom_content);
        TextView textView = findViewById(R.id.content);
        textView.setText(getIntent().getStringExtra("content"));

    }
}