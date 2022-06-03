package com.example.ticketpurchase;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyPopupWindow extends LinearLayout {

    private Button collect;
    private TextView idiom;
    private TextView explanation;

    public MyPopupWindow(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.popup_window,this);
        collect = findViewById(R.id.collect);
        idiom = findViewById(R.id.idiom);
        explanation = findViewById(R.id.explanation);
    }

    public void setIdiom(String idiom) {
        this.idiom.setText(idiom);
    }

    public void setExplanation(String explanation) {
        this.explanation.setText(explanation);
    }

    public void setButtonBackground(Boolean collected) {
        if (collected) {
            this.collect.setBackgroundResource(R.drawable.ic_baseline_star_24);
        }
        else {
            this.collect.setBackgroundResource(R.drawable.ic_baseline_star_border_24);
        }
    }
}
