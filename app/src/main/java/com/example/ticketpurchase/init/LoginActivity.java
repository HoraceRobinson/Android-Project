package com.example.ticketpurchase.init;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.ticketpurchase.home.activity.MainActivity;
import com.example.ticketpurchase.R;
import com.xuexiang.xui.widget.alpha.XUIAlphaTextView;
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

public class LoginActivity extends AppCompatActivity {

    public MaterialEditText username;
    public MaterialEditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        XUIAlphaTextView toRegister = findViewById(R.id.tv_register);
        CheckBox checkBox = findViewById(R.id.cb_protocol);
        SuperButton login = findViewById(R.id.btn_login);
        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        login.setOnClickListener(v -> {
            if(checkBox.isChecked()){
                LoginTask task = new LoginTask(this);
                task.execute();
            }
            else {
                Toast.makeText(LoginActivity.this, "请勾选用户协议", Toast.LENGTH_SHORT).show();
            }
        });

        toRegister.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), RegisterActivity.class)));
    }

    private static class LoginTask extends AsyncTask<Void, Void, String> {

        public static final String URL = "http://47.113.102.111:8080/signIn";
        private final WeakReference<LoginActivity> activityReference;

        public LoginTask (LoginActivity context) {
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
                String args = "/"+ activityReference.get().username.getEditValue() + "/" + activityReference.get().password.getEditValue();
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
            LoginActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e("LoginActivity","Activity弱引用创建失败或Activity已经结束");
                return;
            }
            System.out.println(str);
            System.out.println(JSON.parseObject(str,Boolean.class));
            boolean result = JSON.parseObject(str,Boolean.class);
            if(result){
                SharedPreferences sp = activity.getSharedPreferences("user", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("username", String.valueOf(activity.username.getEditValue()));
                edit.putString("password", String.valueOf(activity.password.getEditValue()));
                edit.apply();
                activity.startActivity(new Intent(activity, MainActivity.class));
                activity.finish();
            }
            else{
                Toast.makeText(activity, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                activity.username.setText(null);
                activity.password.setText(null);
            }
        }
    }
}