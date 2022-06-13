package com.example.ticketpurchase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuexiang.xui.widget.alpha.XUIAlphaImageView;

import java.util.ArrayList;
import java.util.List;

public class FindActivity extends AppCompatActivity {

    public List<TextView> textViews = new ArrayList<>();
    public List<TextView> inputs = new ArrayList<>();
    public int index = 0;
    public String items;
    public String idiom;
    public LinearLayout result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        init();
        for(TextView textView : textViews){
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(index == -1){
                        index = 0;
                    }
                    if(textView.getCurrentTextColor() != getColor(R.color.transform)){
                        inputs.get(index).setText(textView.getText());
                        index++;
                        textView.setTextColor(getColor(R.color.transform));
                        if(index == 4){
                            // judge
                            StringBuilder tmp = new StringBuilder();
                            for(TextView input : inputs){
                                tmp.append(input.getText().toString());
                            }
                            if(tmp.toString() != idiom){
                                for(TextView input : inputs){
                                    input.setText("");
                                }
                                for(TextView textView1 : textViews){
                                    textView1.setTextColor(getColor(R.color.font_black));
                                }
                            }
                            index = 0;
                        }
                    }
                }
            });
        }
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index != 0 && index != -1){
                    for(TextView textView : textViews){
                        if(textView.getText() == inputs.get(index - 1).getText()){
                            textView.setTextColor(getColor(R.color.font_black));
                        }
                    }
                    inputs.get(index - 1).setText("");
                    index--;
                }
            }
        });

        XUIAlphaImageView back = findViewById(R.id.back_func);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void init(){
        items = "中中中中中中中中中中中中中中中中";
        textViews.add(findViewById(R.id.item1));
        textViews.add(findViewById(R.id.item2));
        textViews.add(findViewById(R.id.item3));
        textViews.add(findViewById(R.id.item4));
        textViews.add(findViewById(R.id.item5));
        textViews.add(findViewById(R.id.item6));
        textViews.add(findViewById(R.id.item7));
        textViews.add(findViewById(R.id.item8));
        textViews.add(findViewById(R.id.item9));
        textViews.add(findViewById(R.id.item10));
        textViews.add(findViewById(R.id.item11));
        textViews.add(findViewById(R.id.item12));
        textViews.add(findViewById(R.id.item13));
        textViews.add(findViewById(R.id.item14));
        textViews.add(findViewById(R.id.item15));
        textViews.add(findViewById(R.id.item16));
        inputs.add(findViewById(R.id.text1));
        inputs.add(findViewById(R.id.text2));
        inputs.add(findViewById(R.id.text3));
        inputs.add(findViewById(R.id.text4));
        result = findViewById(R.id.result_layout);
        idiom = "中中中国";
        for(int i = 0; i < 16; i++){
            textViews.get(i).setText("" + items.charAt(i));
        }
    }
}