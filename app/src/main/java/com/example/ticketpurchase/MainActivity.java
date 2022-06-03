package com.example.ticketpurchase;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.xuexiang.xui.widget.popupwindow.popup.XUIPopup;

import org.jetbrains.annotations.TestOnly;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button buttonView;
    TextView textView1;
    TextView textView2;
    EditText editText;
    XUIPopup myPopup;
    View currentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonView = findViewById(R.id.button);
        textView1 = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        editText = findViewById(R.id.input);
        myPopup = new XUIPopup(this);
    }

    public void fight(View view) {
        ObjectAnimator animation0 = ObjectAnimator.ofFloat(textView2, "alpha", 0f,1f);
        animation0.setDuration(2000);
        animation0.start();
        textView2.setText(editText.getText());
        ObjectAnimator animation1 = ObjectAnimator.ofFloat(textView1, "alpha", 0f,1f);
        animation1.setDuration(2000);
        animation1.start();
        textView1.setText("猿鸣三声");
    }

    public void popup(View view) {

        MyPopupWindow window = new MyPopupWindow(this);
        if (view.equals(textView1)) {
            window.setIdiom((String) textView1.getText());
        }
        else {
            window.setIdiom((String) textView2.getText());
        }

//        从ROOM查找数据，找不到就用空星，找到就用实星
        window.setExplanation("啦啦啦");
        if (true) {
            window.setButtonBackground(true);
        } else {
            window.setButtonBackground(false);
        }
        myPopup.setContentView(window);
        myPopup.setAnimStyle(XUIPopup.ANIM_GROW_FROM_CENTER);
        myPopup.setPreferredDirection(XUIPopup.DIRECTION_TOP);
        myPopup.show(view);
        currentView = view;
    }

    public void collect(View view) {

//        根据currentView向ROOM数据库查询是否存在该成语
//        存在则移除，不存在则添加

        if (myPopup.isShowing()) {

            myPopup.dismiss();
        }

    }


}