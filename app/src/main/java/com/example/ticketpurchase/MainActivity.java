package com.example.ticketpurchase;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.xuexiang.xui.widget.button.ButtonView;

public class MainActivity extends AppCompatActivity {

    Button buttonView;
    TextView textView1;
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonView = (Button) findViewById(R.id.button);
        textView1 = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
    }

    public void fight(View view) {
        ObjectAnimator animation1 = ObjectAnimator.ofFloat(textView1, "alpha", 0f,1f);
        animation1.setDuration(2000);
        ObjectAnimator animation2 = ObjectAnimator.ofFloat(textView1, "translationX", 100f);
        animation1.setDuration(2000);
        animation1.start();
        animation2.start();
    }
}