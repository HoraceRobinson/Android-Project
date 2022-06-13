package com.example.ticketpurchase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xuexiang.xui.widget.alpha.XUIAlphaImageView;
import com.xuexiang.xui.widget.progress.CircleProgressView;
import com.xuexiang.xui.widget.progress.HorizontalProgressView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FindActivity extends AppCompatActivity {

    public List<TextView> textViews = new ArrayList<>();
    public List<TextView> inputs = new ArrayList<>();
    public int index = 0;
    public String items;
    public String idiom;
    public LinearLayout result;
    public TextView scoreText;
    public Timer timer;
    public int score = 0;
    public LinearLayout textGrid;
    public TextView start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        init();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textGrid.setVisibility(View.VISIBLE);
                start.setVisibility(View.GONE);
                setContent();
                HorizontalProgressView progressView = findViewById(R.id.line_progress);
                progressView.startProgressAnimation();

                Handler handler = new Handler(){
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        super.handleMessage(msg);
                        if(msg.what >= 0){
                        }
                        else{
                            start.setText("再来一次");
                            start.setVisibility(View.VISIBLE);
                            textGrid.setVisibility(View.GONE);
                            timer.cancel();
                        }
                    }
                };

                timer = new Timer();
                timer.schedule(new TimerTask() {
                    int i = 60;
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.what = i--;
                        handler.sendMessage(msg);
                    }
                }, 0, 1000);
            }
        });



        for(TextView textView : textViews){
            textView.setOnClickListener(view -> {
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
                        if(!tmp.toString().equals(idiom)){
                            for(TextView input : inputs){
                                input.setText("");
                            }
                            for(TextView textView1 : textViews){
                                textView1.setTextColor(getColor(R.color.font_black));
                            }
                        }
                        else{
                            score++;
                            scoreText.setText("" + score);
                            setContent();
                        }
                        index = 0;
                    }
                }
            });
        }
        result.setOnClickListener(view -> {
            if(index != 0 && index != -1){
                for(TextView textView : textViews){
                    if(textView.getText() == inputs.get(index - 1).getText()){
                        textView.setTextColor(getColor(R.color.font_black));
                    }
                }
                inputs.get(index - 1).setText("");
                index--;
            }
        });

        XUIAlphaImageView back = findViewById(R.id.back_func);
        back.setOnClickListener(view -> finish());

    }

    public void init(){
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
        scoreText = findViewById(R.id.score);
        textGrid = findViewById(R.id.text_grid);
        start = findViewById(R.id.start_game);
    }

    public void setContent(){
        IdiomChooseTask task = new IdiomChooseTask(this);
        task.execute();

        for(int i = 0; i < 4; i++){
            inputs.get(i).setText("");
        }
        scoreText.setText("0");
    }

    private static class IdiomChooseTask extends AsyncTask<Void, Void, String> {

        public static final String URL = "http://47.113.102.111:8080/chooseCorrectIdiom";
        private final WeakReference<FindActivity> activityReference;

        public IdiomChooseTask  (FindActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected String doInBackground(Void ...voids) {
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;
            String tmp;
            StringBuilder stringBuilder = new StringBuilder();
            try {
                java.net.URL url = new URL(URL);
                URLConnection connection = url.openConnection();
                connection.connect();
                inputStream = connection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                bufferedReader = new BufferedReader(inputStreamReader);
                while ((tmp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(tmp);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String str) {
            FindActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e("FindActivity","Activity弱引用创建失败或Activity已经结束");
                return;
            }
            List<String> result = JSONObject.parseArray(str,String.class);
            activity.idiom = result.get(0);
            activity.items = result.get(1);
            for(int i = 0; i < 16; i++){
                activity.textViews.get(i).setText("" + activity.items.charAt(i));
            }
        }
    }
}