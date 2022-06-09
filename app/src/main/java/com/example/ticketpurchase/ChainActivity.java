package com.example.ticketpurchase;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ticketpurchase.room.DBEngine;
import com.xuexiang.xui.widget.alpha.XUIAlphaImageView;
import com.xuexiang.xui.widget.popupwindow.popup.XUIPopup;

public class ChainActivity extends AppCompatActivity {

    Button buttonView;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;
    TextView textView8;
    LinearLayout layout1;
    LinearLayout layout2;
    EditText editText;
    public XUIPopup myPopup;
    DBEngine dbEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chain);
        buttonView = findViewById(R.id.button);
        textView1 = findViewById(R.id.text1);
        textView2 = findViewById(R.id.text2);
        textView3 = findViewById(R.id.text3);
        textView4 = findViewById(R.id.text4);
        textView5 = findViewById(R.id.text5);
        textView6 = findViewById(R.id.text6);
        textView7 = findViewById(R.id.text7);
        textView8 = findViewById(R.id.text8);
        editText = findViewById(R.id.input);
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        myPopup = new XUIPopup(this);
        dbEngine = new DBEngine(this);

        XUIAlphaImageView imageView = findViewById(R.id.back_func);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void chain(View view) throws InterruptedException {
        String input = editText.getText().toString();
        //用异步任务请求数据库，判断input是否为成语
//        ObjectAnimator animation2 = ObjectAnimator.ofFloat(layout2, "alpha", 0f,1f);
//        animation2.setDuration(1000);
//        animation2.start();
        textView5.setText(((Character) input.charAt(0)).toString());
        textView6.setText(((Character) input.charAt(1)).toString());
        textView7.setText(((Character) input.charAt(2)).toString());
        textView8.setText(((Character) input.charAt(3)).toString());
        ObjectAnimator animation1 = ObjectAnimator.ofFloat(layout1, "alpha", 0f,1f);
        animation1.setDuration(2000);
        animation1.start();
        //用异步任务查询数据库中前缀为input的后缀的成语
        textView1.setText("拔");
        textView2.setText("苗");
        textView3.setText("助");
        textView4.setText("长");
    }

    public void popup(View view) {
        String str = textView1.getText().toString()+textView2.getText().toString()+textView3.getText().toString()+textView4.getText().toString();
        dbEngine.popupIdiom(str, this);
    }

    public void collect(View view) {
//        存在则移除，不存在则添加
        if (myPopup.isShowing()) {
            String str = textView1.getText().toString()+textView2.getText().toString()+textView3.getText().toString()+textView4.getText().toString();
            myPopup.dismiss();
            dbEngine.collectIdiom(str,this);
        }
    }
}