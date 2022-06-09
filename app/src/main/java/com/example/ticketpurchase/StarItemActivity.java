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
//        Idiom idiom1 = new Idiom("贪生怕死","[ tān shēng pà sǐ ]","贪恋生存，害怕死亡。 ","明·罗贯中《三国演义》：“贪生怕死之徒；不足以论大事。”","人们最看不起正义战争战场上的贪生怕死的逃兵。");
//        Idiom idiom2 = new Idiom("天网恢恢","[ tiān wǎng huī huī ]","恢恢：形容非常广大。形容作恶者一定受到惩罚。 ","《老子》:“天网恢恢，疏而不失。”","他杀人后就逃走了，殊知天网恢恢，疏而不漏，终于还是被捕归案。");
//        Idiom idiom3 = new Idiom("拔苗助长","[ bá miáo zhù zhǎng ]","比喻违反事物的发展规律，急于求成，最后事与愿违。","郭沫若《雄鸡集·关于发展学术与文艺的问题》：“命令主义就合乎中国古代的一个寓言，叫做‘拔苗助长’。结果被拔起的苗不仅不能成长，反而枯槁了。”","那种不顾学生能否接受的填鸭式的教学方法，无异于拔苗助长。");
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