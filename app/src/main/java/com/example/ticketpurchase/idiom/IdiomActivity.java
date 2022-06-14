package com.example.ticketpurchase.idiom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.ticketpurchase.R;
import com.example.ticketpurchase.extendview.MyPopupWindow;
import com.example.ticketpurchase.room.DBEngine;
import com.example.ticketpurchase.room.Idiom;
import com.example.ticketpurchase.room.IdiomDao;
import com.xuexiang.xui.widget.alpha.XUIAlphaImageView;
import com.xuexiang.xui.widget.popupwindow.popup.XUIPopup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class IdiomActivity extends AppCompatActivity {

    private DBEngine dbEngine;
    public TextView textView1;
    public TextView textView2;
    public TextView textView3;
    public TextView textView4;
    public TextView pinyin;
    public TextView meaning;
    public TextView reference;
    public TextView example;
    public XUIAlphaImageView star;
    public boolean isStarred;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idiom);
        textView1 = findViewById(R.id.text1);
        textView2 = findViewById(R.id.text2);
        textView3 = findViewById(R.id.text3);
        textView4 = findViewById(R.id.text4);
        pinyin = findViewById(R.id.pinyin);
        meaning = findViewById(R.id.meaning);
        reference = findViewById(R.id.reference);
        example = findViewById(R.id.example);
        star = findViewById(R.id.star);

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
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String arg1 = textView1.getText().toString()+textView2.getText().toString()+textView3.getText().toString()+textView4.getText().toString();
                String arg2 = pinyin.getText().toString();
                String arg3 = meaning.getText().toString();
                String arg4 = reference.getText().toString();
                String arg5 = example.getText().toString();
                Idiom idiom = new Idiom(arg1, arg2, arg3, arg4, arg5);
                if(isStarred){
                    dbEngine.deleteIdioms(idiom);
                    star.setImageDrawable(getDrawable(R.drawable.ic_star));
                }
                else {
                    dbEngine.insertIdioms(idiom);
                    star.setImageDrawable(getDrawable(R.drawable.ic_star_fill));
                }
                isStarred = !isStarred;
            }
        });
    }


}