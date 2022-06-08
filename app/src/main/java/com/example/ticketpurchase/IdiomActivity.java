package com.example.ticketpurchase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.xuexiang.xui.widget.alpha.XUIAlphaImageView;

public class IdiomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idiom);

        XUIAlphaImageView backList = findViewById(R.id.back_list);
        backList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        
    }
}