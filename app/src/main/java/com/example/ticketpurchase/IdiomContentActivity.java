package com.example.ticketpurchase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ticketpurchase.room.DBEngine;
import com.example.ticketpurchase.room.Idiom;

public class IdiomContentActivity extends AppCompatActivity {

    private DBEngine dbEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idiom_content);
        Intent intent = getIntent();
        String idiom_text = intent.getStringExtra("idiom_text");
        dbEngine = new DBEngine(this);
        dbEngine.getContentIdiom(idiom_text, this);
    }
}