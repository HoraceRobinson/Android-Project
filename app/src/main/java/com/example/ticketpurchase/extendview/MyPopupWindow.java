package com.example.ticketpurchase.extendview;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ticketpurchase.R;

public class MyPopupWindow extends LinearLayout {

    private Button collect;
    private TextView idiom_text;
    private TextView explanation;

    public MyPopupWindow(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.popup_window,this);
        collect = findViewById(R.id.collect);
        idiom_text = findViewById(R.id.idiom_text);
        explanation = findViewById(R.id.explanation);
    }

    public void setIdiom(String idiom) {
        this.idiom_text.setText(idiom);
    }

    public void setExplanation(String explanation) {
        this.explanation.setText(explanation);
    }

    public void setButtonBackground(Boolean collected) {
        if (collected) {
            this.collect.setBackgroundResource(R.drawable.ic_star_fill);
        }
        else {
            this.collect.setBackgroundResource(R.drawable.ic_star);
        }
    }
}
