package com.example.ticketpurchase;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.ticketpurchase.room.DBEngine;
import com.xuexiang.xui.widget.alpha.XUIAlphaImageView;
import com.xuexiang.xui.widget.popupwindow.popup.XUIPopup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
        myPopup = new XUIPopup(this);
        dbEngine = new DBEngine(this);
        IdiomInitializeTask task = new IdiomInitializeTask(this);
        task.execute();
        XUIAlphaImageView imageView = findViewById(R.id.back_func);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private static class IdiomInitializeTask extends AsyncTask<Void, Void, String> {

        public static final String URL = "http://47.113.102.111:8080/getOneRandomIdiom";
        private final WeakReference <ChainActivity> activityReference;

        public IdiomInitializeTask  (ChainActivity context) {
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
            ChainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e("ChainActivity","Activity弱引用创建失败或Activity已经结束");
                return;
            }
            JSONObject data = JSON.parseObject(str);
            String name = data.getString("idiom");
            TextView textView1 = activity.findViewById(R.id.text1);
            TextView textView2 = activity.findViewById(R.id.text2);
            TextView textView3 = activity.findViewById(R.id.text3);
            TextView textView4 = activity.findViewById(R.id.text4);
            textView1.setText(((Character) name.charAt(0)).toString());
            textView2.setText(((Character) name.charAt(1)).toString());
            textView3.setText(((Character) name.charAt(2)).toString());
            textView4.setText(((Character) name.charAt(3)).toString());
        }
    }

    public void chain(View view) throws InterruptedException {
        String input = editText.getText().toString();
        if (input.charAt(0) != textView4.getText().charAt(0)) {
            Toast.makeText(this,"输入成语不符合要求",Toast.LENGTH_SHORT).show();
        }
        else {
            IdiomExistTask task = new IdiomExistTask(this);
            task.execute(input);
        }

    }

    private static class IdiomExistTask extends AsyncTask<String, Void, String> {

        public static final String URL = "http://47.113.102.111:8080/findIdiomByName/";
        private final WeakReference <ChainActivity> activityReference;

        public IdiomExistTask  (ChainActivity context) {
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
        ChainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e("ChainActivity","Activity弱引用创建失败或Activity已经结束");
                return;
            }
            JSONObject data = JSON.parseObject(str);
            if (data == null) {
                Toast.makeText(activity,"此成语不存在",Toast.LENGTH_SHORT).show();
            }
            else {
                String name = data.getString("idiom");
                TextView textView5 = activity.findViewById(R.id.text5);
                TextView textView6 = activity.findViewById(R.id.text6);
                TextView textView7 = activity.findViewById(R.id.text7);
                TextView textView8 = activity.findViewById(R.id.text8);
                textView5.setText(((Character) name.charAt(0)).toString());
                textView6.setText(((Character) name.charAt(1)).toString());
                textView7.setText(((Character) name.charAt(2)).toString());
                textView8.setText(((Character) name.charAt(3)).toString());
                IdiomChainTask task = new IdiomChainTask(activity);
                task.execute(name);
            }
        }
    }

    private static class IdiomChainTask extends AsyncTask<String, Void, String> {

        public static final String URL = "http://47.113.102.111:8080/findIdiomChain/";
        private final WeakReference <ChainActivity> activityReference;

        public IdiomChainTask (ChainActivity context) {
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
        ChainActivity activity = activityReference.get();
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
                String name = data.getString("idiom");
                TextView textView1 = activity.findViewById(R.id.text1);
                TextView textView2 = activity.findViewById(R.id.text2);
                TextView textView3 = activity.findViewById(R.id.text3);
                TextView textView4 = activity.findViewById(R.id.text4);
                LinearLayout layout = activity.findViewById(R.id.layout1);
                textView1.setText(((Character) name.charAt(0)).toString());
                textView2.setText(((Character) name.charAt(1)).toString());
                textView3.setText(((Character) name.charAt(2)).toString());
                textView4.setText(((Character) name.charAt(3)).toString());
                ObjectAnimator animation = ObjectAnimator.ofFloat(layout, "alpha", 0f,1f);
                animation.setDuration(2000);
                animation.start();
            }
        }
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