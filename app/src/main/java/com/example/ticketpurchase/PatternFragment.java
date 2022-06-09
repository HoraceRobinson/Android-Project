package com.example.ticketpurchase;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatternFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatternFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public PatternFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PatternFragment newInstance() {
        PatternFragment fragment = new PatternFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pattern, container, false);

        Button btn1 = view.findViewById(R.id.btn1);
        Button btn2 = view.findViewById(R.id.btn2);
        Button btn3 = view.findViewById(R.id.btn3);
        Button btn4 = view.findViewById(R.id.btn4);
        btn1.setText("振聋发聩");
        btn2.setText("震聋发聩");
        btn3.setText("振聋发溃");
        btn4.setText("震聋发溃");
        TextView textView = view.findViewById(R.id.text_explain);
        LinearLayout linearLayout = view.findViewById(R.id.next_btn);
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
                btn1.setText("浑金璞玉");
                btn2.setText("混金璞玉");
                btn3.setText("浑金镤玉");
                btn4.setText("混金镤玉");
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

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}