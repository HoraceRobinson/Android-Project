package com.example.ticketpurchase;

import android.animation.ObjectAnimator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.ticketpurchase.room.DBEngine;
import com.xuexiang.xui.widget.popupwindow.popup.XUIPopup;

import org.jetbrains.annotations.TestOnly;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
//        ObjectAnimator animation2 = ObjectAnimator.ofFloat(layout2, "alpha", 0f,1f);
//        animation2.setDuration(1000);
//        animation2.start();
        //用异步任务查询数据库中前缀为input的后缀的成语
        IdiomExistTask task = new IdiomExistTask(this);
        task.execute(input);
        IdiomChainTask task1 = new IdiomChainTask(this);
        task1.execute(input);
//        textView1.setText("拔");
//        textView2.setText("苗");
//        textView3.setText("助");
//        textView4.setText("长");
//        String input = editText.getText().toString();
//        //用异步任务请求数据库，判断input是否为成语
//        ObjectAnimator animation2 = ObjectAnimator.ofFloat(textView2, "alpha", 0f,1f);
//        animation2.setDuration(2000);
//        animation2.start();
//        textView2.setText(input);
//        ObjectAnimator animation1 = ObjectAnimator.ofFloat(textView1, "alpha", 0f,1f);
//        animation1.setDuration(2000);
//        animation1.start();
//        //用异步任务查询数据库中前缀为input的后缀的成语
//        textView1.setText("猿鸣三声");
    }

    private static class IdiomExistTask extends AsyncTask<String, Void, String> {

        public static final String URL = "http://47.113.102.111:8080/findIdiomByName/";
        private final WeakReference<MainActivity> activityReference;

        public IdiomExistTask(MainActivity context) {
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
            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e("ChainActivity","Activity弱引用创建失败或Activity已经结束");
                return;
            }
            JSONObject data = JSON.parseObject(str);
            if (data == null) {
                //makeText并且不置值
                Toast.makeText(activity,"此成语不存在",Toast.LENGTH_SHORT).show();
            }
            else {
                //为输入四个字置值
                System.out.println(data.getString("idiom"));
//                textView5.setText(((Character) input.charAt(0)).toString());
//                textView6.setText(((Character) input.charAt(1)).toString());
//                textView7.setText(((Character) input.charAt(2)).toString());
//                textView8.setText(((Character) input.charAt(3)).toString());
            }
        }
    }

    private static class IdiomChainTask extends AsyncTask<String, Void, String> {

        public static final String URL = "http://47.113.102.111:8080/findIdiomChain/";
        private final WeakReference<MainActivity> activityReference;

        public IdiomChainTask(MainActivity context) {
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
            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e("ChainActivity","Activity弱引用创建失败或Activity已经结束");
                return;
            }
            JSONObject data = JSON.parseObject(str);
            if (data == null) {
                //makeText并且不置值
                Toast.makeText(activity,"数据库被你打败了",Toast.LENGTH_SHORT).show();
            }
            else {
                //为上面四个字置值并动画显示
                System.out.println(data.getString("idiom"));
//                textView5.setText(((Character) input.charAt(0)).toString());
//                textView6.setText(((Character) input.charAt(1)).toString());
//                textView7.setText(((Character) input.charAt(2)).toString());
//                textView8.setText(((Character) input.charAt(3)).toString());
//                ObjectAnimator animation1 = ObjectAnimator.ofFloat(layout1, "alpha", 0f,1f);
//                animation1.setDuration(2000);
//                animation1.start();
            }
        }
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