package com.example.ticketpurchase.init;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.ticketpurchase.R;
import com.xuexiang.xui.widget.alpha.XUIAlphaImageView;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class RegisterActivity extends AppCompatActivity {

    public MaterialEditText username;
    public MaterialEditText password;
    public int sex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        CheckBox checkBox = findViewById(R.id.cb_protocol);
        SuperButton register = findViewById(R.id.btn_register);
        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()){
                    RegisterTask task = new RegisterTask(RegisterActivity.this);
                    task.execute();
                }
                else {
                    Toast.makeText(RegisterActivity.this, "请勾选用户协议", Toast.LENGTH_SHORT).show();
                }
            }
        });
        XUIAlphaImageView man = findViewById(R.id.man);
        man.setImageDrawable(getDrawable(R.drawable.ic_man_fill));
        XUIAlphaImageView woman = findViewById(R.id.woman);
        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                man.setImageDrawable(getDrawable(R.drawable.ic_man_fill));
                woman.setImageDrawable(getDrawable(R.drawable.ic_woman));
                sex = 1;
            }
        });
        woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                woman.setImageDrawable(getDrawable(R.drawable.ic_woman_fill));
                man.setImageDrawable(getDrawable(R.drawable.ic_man));
                sex = 0;
            }
        });
    }

    private static class RegisterTask extends AsyncTask<Void, Void, String> {

        public static final String URL = "http://47.113.102.111:8080/signUp";
        private final WeakReference<RegisterActivity> activityReference;

        public RegisterTask (RegisterActivity context) {
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
                String args = "/"+ activityReference.get().username.getEditValue() + "/" + activityReference.get().password.getEditValue() + "/" + activityReference.get().sex;
                java.net.URL url = new URL( URL + args);
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
            RegisterActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e("RegisterActivity","Activity弱引用创建失败或Activity已经结束");
                return;
            }
            boolean result = JSON.parseObject(str,Boolean.class);
            if(result){
                activity.finish();
            }
            else{
                Toast.makeText(activity, "用户名已存在", Toast.LENGTH_SHORT).show();
                activity.username.setText(null);
                activity.password.setText(null);
            }
        }
    }

}