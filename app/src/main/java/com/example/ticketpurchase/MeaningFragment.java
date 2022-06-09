package com.example.ticketpurchase;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeaningFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeaningFragment extends Fragment {


    public MeaningFragment() {
        // Required empty public constructor
    }

    public static MeaningFragment newInstance() {
        MeaningFragment fragment = new MeaningFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meaning, container, false);
        TextView textView1 = view.findViewById(R.id.text1);
        TextView textView2 = view.findViewById(R.id.text2);
        TextView textView3 = view.findViewById(R.id.text3);
        TextView textView4 = view.findViewById(R.id.text4);

        Button btn1 = view.findViewById(R.id.btn1);
        Button btn2 = view.findViewById(R.id.btn2);
        Button btn3 = view.findViewById(R.id.btn3);
        Button btn4 = view.findViewById(R.id.btn4);

        TextView textView = view.findViewById(R.id.text_explain);
        LinearLayout linearLayout = view.findViewById(R.id.next_btn);

        textView1.setText("高");
        textView2.setText("山");
        textView3.setText("流");
        textView4.setText("水");

        btn1.setText("比喻人生知音难遇，也指乐曲弹奏非常绝妙");
        btn2.setText("描述自然现象");
        btn3.setText("形容事物自然流畅");
        btn4.setText("形容朋友之间十分默契");

        btn1.setOnClickListener(view14 -> {
            btn1.setBackground(getResources().getDrawable(R.drawable.background_green));
            btn1.setTextColor(getResources().getColor(R.color.white));
            if(textView.getText() == ""){
                textView.setText("回答正确！");
            }
        });

        btn2.setOnClickListener(view13 -> {
            btn2.setBackground(getResources().getDrawable(R.drawable.background_red));
            btn2.setTextColor(getResources().getColor(R.color.white));
            if(textView.getText() == ""){
                textView.setText("回答错误！");
            }
        });

        btn3.setOnClickListener(view1 -> {
            btn3.setBackground(getResources().getDrawable(R.drawable.background_red));
            btn3.setTextColor(getResources().getColor(R.color.white));
            if(textView.getText() == ""){
                textView.setText("回答错误！");
            }
        });

        btn4.setOnClickListener(view12 -> {
            btn4.setBackground(getResources().getDrawable(R.drawable.background_red));
            btn4.setTextColor(getResources().getColor(R.color.white));
            if(textView.getText() == ""){
                textView.setText("回答错误！");
            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView1.setText("洛");
                textView2.setText("阳");
                textView3.setText("纸");
                textView4.setText("贵");
                btn1.setText("形容文学作品十分受人欢迎，广泛流传");
                btn2.setText("形容物价暴涨");
                btn3.setText("形容商品抢手，千金难求");
                btn4.setText("形容战争时期家书难得");
                btn1.setTextColor(getResources().getColor(R.color.black));
                btn2.setTextColor(getResources().getColor(R.color.black));
                btn3.setTextColor(getResources().getColor(R.color.black));
                btn4.setTextColor(getResources().getColor(R.color.black));
                btn1.setBackground(getResources().getDrawable(R.drawable.white_background));
                btn2.setBackground(getResources().getDrawable(R.drawable.white_background));
                btn3.setBackground(getResources().getDrawable(R.drawable.white_background));
                btn4.setBackground(getResources().getDrawable(R.drawable.white_background));
                textView.setText("");
            }
        });
        return view;
    }
}