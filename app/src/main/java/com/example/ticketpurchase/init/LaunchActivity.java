package com.example.ticketpurchase.init;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.ticketpurchase.R;
import com.example.ticketpurchase.home.activity.MainActivity;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                String username = sharedPreferences.getString("username", null);
                String password = sharedPreferences.getString("password", null);
                if(username == null){
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                LaunchActivity.this.finish();
            }
        }, 3000);
    }
}