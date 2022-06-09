package com.example.ticketpurchase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ticketpurchase.room.DBEngine;
import com.xuexiang.xui.widget.alpha.XUIAlphaImageView;

public class IdiomActivity extends AppCompatActivity {

    private DBEngine dbEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idiom);

        Intent intent = getIntent();
        String idiom_text = intent.getStringExtra("idiom_text");
        dbEngine = new DBEngine(this);
        dbEngine.getContentIdiom(idiom_text, this);

        XUIAlphaImageView backList = findViewById(R.id.back_list);
        backList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        
    }
}