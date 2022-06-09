package com.example.ticketpurchase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.ticketpurchase.adapter.IdiomAdapter;
import com.example.ticketpurchase.room.DBEngine;
import com.example.ticketpurchase.room.Idiom;
import com.xuexiang.xui.widget.alpha.XUIAlphaImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StarItemActivity extends AppCompatActivity {

    public JSONArray jsonArray = new JSONArray();
    private DBEngine dbEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_item);
//        Idiom idiom1 = new Idiom("一夫当关","yifudangguan","一个人在关","出处","例句");
//        Idiom idiom2 = new Idiom("万夫莫开","wanfumokai","万个人卜凯","出处","例句");
//        Idiom idiom3 = new Idiom("杀人如麻","sharenruma","杀人像嘛","出处","例句");
        dbEngine = new DBEngine(this);
//        dbEngine.insertIdioms(idiom1,idiom2,idiom3);

        dbEngine.getAllIdioms(this);

        try {
            initData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RecyclerView recyclerView = findViewById(R.id.idiom_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        IdiomAdapter idiomAdapter = new IdiomAdapter(jsonArray, this);
        recyclerView.setAdapter(idiomAdapter);

        XUIAlphaImageView backUser = findViewById(R.id.back_user);
        backUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData() throws JSONException {
        for(int i = 0; i < 5; i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idiom", "贪生怕死");
            jsonObject.put("pinyin", "[ tān shēng pà sǐ ]");
            jsonObject.put("shiyi", "贪恋生存，害怕死亡。 ");
            jsonArray.put(jsonObject);
        }
    }
}