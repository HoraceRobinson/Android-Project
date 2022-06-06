package com.example.ticketpurchase;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ticketpurchase.room.DBEngine;
import com.xuexiang.xui.widget.popupwindow.popup.XUIPopup;

import org.jetbrains.annotations.TestOnly;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button buttonView;
    TextView textView1;
    TextView textView2;
    EditText editText;
    public XUIPopup myPopup;
    DBEngine dbEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonView = findViewById(R.id.button);
        textView1 = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        editText = findViewById(R.id.input);
        myPopup = new XUIPopup(this);
        dbEngine = new DBEngine(this);
    }

    public void chain(View view) {
        String input = editText.getText().toString();
        //用异步任务请求数据库，判断input是否为成语
        ObjectAnimator animation2 = ObjectAnimator.ofFloat(textView2, "alpha", 0f,1f);
        animation2.setDuration(2000);
        animation2.start();
        textView2.setText(input);
        ObjectAnimator animation1 = ObjectAnimator.ofFloat(textView1, "alpha", 0f,1f);
        animation1.setDuration(2000);
        animation1.start();
        //用异步任务查询数据库中前缀为input的后缀的成语
        textView1.setText("猿鸣三声");
    }

    public void popup(View view) {
        dbEngine.popupIdiom((String) textView1.getText(), this);
    }

    public void collect(View view) {
//        存在则移除，不存在则添加
        if (myPopup.isShowing()) {
            myPopup.dismiss();
            dbEngine.collectIdiom((String) textView1.getText(),this);
        }
    }
}