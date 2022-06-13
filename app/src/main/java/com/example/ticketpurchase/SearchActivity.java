package com.example.ticketpurchase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.ticketpurchase.adapter.SearchAdapter;
import com.example.ticketpurchase.adapter.StarAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        String searchKey = intent.getStringExtra("search_key");
        System.out.println(searchKey);
        IdiomSearchTask task = new IdiomSearchTask(this);
        task.execute(searchKey);
    }

    private static class IdiomSearchTask extends AsyncTask<String, Void, String> {

        public static final String URL = "http://47.113.102.111:8080/findIdiomByLikelyName/";
        private final WeakReference<SearchActivity> activityReference;

        public IdiomSearchTask (SearchActivity context) {
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
            SearchActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e("SearchActivity","Activity弱引用创建失败或Activity已经结束");
                return;
            }
            if (str == null) {
                //makeText并且不置值
                Toast.makeText(activity,"没有找到相关结果",Toast.LENGTH_SHORT).show();
            }
            else {
                List<String> search_result = JSONObject.parseArray(str,String.class);
                RecyclerView recyclerView = activity.findViewById(R.id.result_list);
                recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                recyclerView.setAdapter(new SearchAdapter(search_result,activity));
            }
        }
    }
}