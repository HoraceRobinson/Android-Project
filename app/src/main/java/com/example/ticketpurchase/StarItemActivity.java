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
        dbEngine = new DBEngine(this);

        dbEngine.getAllIdioms(this);
        XUIAlphaImageView backUser = findViewById(R.id.back_user);
        backUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbEngine.getAllIdioms(this);
    }
}