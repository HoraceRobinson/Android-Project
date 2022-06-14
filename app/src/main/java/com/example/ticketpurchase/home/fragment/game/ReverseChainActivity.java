package com.example.ticketpurchase.home.fragment.game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.ticketpurchase.R;
import com.example.ticketpurchase.room.DBEngine;
import com.xuexiang.xui.widget.alpha.XUIAlphaImageView;
import com.xuexiang.xui.widget.popupwindow.popup.XUIPopup;
import com.xuexiang.xui.widget.progress.CircleProgressView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;

public class ReverseChainActivity extends AppCompatActivity {

    Button buttonView;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;
    TextView textView8;
    EditText editText;
    TextView time;
    TextView score;
    public XUIPopup myPopup;
    private DBEngine dbEngine;

    int cnt = 0;

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reverse_chain);
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
        score = findViewById(R.id.score);
        time = findViewById(R.id.time);
        myPopup = new XUIPopup(this);
        dbEngine = new DBEngine(this);
        ReverseChainActivity.IdiomInitializeTask task = new ReverseChainActivity.IdiomInitializeTask(this);
        task.execute();
        XUIAlphaImageView imageView = findViewById(R.id.back_func);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView start = findViewById(R.id.start_game);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout layout2 = findViewById(R.id.layout2);
                layout2.setVisibility(View.VISIBLE);
                LinearLayout input = findViewById(R.id.input_text);
                input.setVisibility(View.VISIBLE);
                View divider = findViewById(R.id.divider);
                divider.setVisibility(View.VISIBLE);
                LinearLayout timeLayout = findViewById(R.id.time_layout);
                timeLayout.setVisibility(View.VISIBLE);
                start.setVisibility(View.GONE);

                CircleProgressView progressView = findViewById(R.id.progressView_circle_small);
                progressView.startProgressAnimation();
                Handler handler = new Handler(){
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        super.handleMessage(msg);
                        if(msg.what >= 0){
                            time.setText("" + msg.what);
                        }
                        else{
                            layout2.setVisibility(View.GONE);
                            input.setVisibility(View.GONE);
                            divider.setVisibility(View.GONE);
                            start.setText("再来一次");
                            start.setVisibility(View.VISIBLE);
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

    }

    private static class IdiomInitializeTask extends AsyncTask<Void, Void, String> {

        public static final String URL = "http://47.113.102.111:8080/getOneRandomIdiom";
        private final WeakReference<ReverseChainActivity> activityReference;

        public IdiomInitializeTask  (ReverseChainActivity context) {
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
                java.net.URL url = new URL( URL );
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
            ReverseChainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e("ChainActivity","Activity弱引用创建失败或Activity已经结束");
                return;
            }
            JSONObject data = JSON.parseObject(str);
            String name = data.getString("idiom");
            activity.textView1.setText(((Character) name.charAt(0)).toString());
            activity.textView2.setText(((Character) name.charAt(1)).toString());
            activity.textView3.setText(((Character) name.charAt(2)).toString());
            activity.textView4.setText(((Character) name.charAt(3)).toString());
        }
    }

    public void chain(View view) throws InterruptedException {
        String input = editText.getText().toString();
        editText.setText("");
        if (input.charAt(input.length() - 1) != textView1.getText().charAt(0)) {
            Toast.makeText(this,"输入成语不符合要求",Toast.LENGTH_SHORT).show();
        }
        else {
            ReverseChainActivity.IdiomExistTask task = new ReverseChainActivity.IdiomExistTask(this);
            task.execute(input);
        }

    }

    private static class IdiomExistTask extends AsyncTask<String, Void, String> {

        public static final String URL = "http://47.113.102.111:8080/findIdiomByName/";
        private final WeakReference <ReverseChainActivity> activityReference;

        public IdiomExistTask  (ReverseChainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected String doInBackground(String... strings) {
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;
            String tmp;
            StringBuilder stringBuilder = new StringBuilder();
            try {
                java.net.URL url = new URL( URL + URLEncoder.encode(strings[0], "UTF-8"));
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
            ReverseChainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e("ReverseChainActivity","Activity弱引用创建失败或Activity已经结束");
                return;
            }
            JSONObject data = JSON.parseObject(str);
            if (data == null) {
                Toast.makeText(activity,"此成语不存在",Toast.LENGTH_SHORT).show();
            }
            else {
                String name = data.getString("idiom");
                activity.textView5.setText(((Character) name.charAt(0)).toString());
                activity.textView6.setText(((Character) name.charAt(1)).toString());
                activity.textView7.setText(((Character) name.charAt(2)).toString());
                activity.textView8.setText(((Character) name.charAt(3)).toString());
                activity.cnt++;
                activity.score.setText("" + activity.cnt);
                ReverseChainActivity.ReverseIdiomChainTask task = new ReverseChainActivity.ReverseIdiomChainTask(activity);
                task.execute(activity.textView5.getText().toString());
            }
        }
    }

    private static class ReverseIdiomChainTask extends AsyncTask<String, Void, String> {

        public static final String URL = "http://47.113.102.111:8080/findReverseIdiomChain/";
        private final WeakReference <ReverseChainActivity> activityReference;

        public ReverseIdiomChainTask (ReverseChainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected String doInBackground(String... strings) {
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;
            String tmp;
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL( URL + URLEncoder.encode(strings[0], "UTF-8"));
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
            ReverseChainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e("ReverseChainActivity","Activity弱引用创建失败或Activity已经结束");
                return;
            }
//            JSONObject data = JSON.parseObject(str);
            if (str == null) {
                //makeText并且不置值
                Toast.makeText(activity,"数据库被你打败了",Toast.LENGTH_SHORT).show();
            }
            else {
                String name = str;
                LinearLayout layout = activity.findViewById(R.id.layout1);
                activity.textView1.setText(((Character) name.charAt(0)).toString());
                activity.textView2.setText(((Character) name.charAt(1)).toString());
                activity.textView3.setText(((Character) name.charAt(2)).toString());
                activity.textView4.setText(((Character) name.charAt(3)).toString());
                ObjectAnimator animation = ObjectAnimator.ofFloat(layout, "alpha", 0f,1f);
                animation.setDuration(2000);
                animation.start();
            }
        }
    }

    public void popup(View view) {
        String str = textView1.getText().toString()+textView2.getText().toString()+textView3.getText().toString()+textView4.getText().toString();
        dbEngine.popupReverseIdiom(str, this);
    }

    public void collect(View view) {
//        存在则移除，不存在则添加
        if (myPopup.isShowing()) {
            String str = textView1.getText().toString()+textView2.getText().toString()+textView3.getText().toString()+textView4.getText().toString();
            myPopup.dismiss();
            dbEngine.collectReverseIdiom(str,this);
        }
    }
}