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
 * Use the {@link PronunceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PronunceFragment extends Fragment {


    public PronunceFragment() {
        // Required empty public constructor
    }

    public static PronunceFragment newInstance() {
        PronunceFragment fragment = new PronunceFragment();
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
        View view = inflater.inflate(R.layout.fragment_pronunce, container, false);
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

        textView1.setText("差");
        textView2.setText("强");
        textView3.setText("人");
        textView4.setText("意");

        btn1.setText("[ chā qiáng rén yì ]");
        btn2.setText("[ chà qiáng rén yì ]");
        btn3.setText("[ chà qiǎng rén yì ]");
        btn4.setText("[ chā qiǎng rén yì ]");

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
                textView1.setText("度");
                textView2.setText("德");
                textView3.setText("量");
                textView4.setText("力");
                btn1.setText("[ duó dé liàng lì ]");
                btn2.setText("[ dù dé liàng lì ]");
                btn3.setText("[ duó dé liáng lì ]");
                btn4.setText("[ dù dé liáng lì ]");
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