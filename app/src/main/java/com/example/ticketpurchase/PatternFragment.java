package com.example.ticketpurchase;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.ticketpurchase.room.XingIdiom;

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


public class PatternFragment extends Fragment {

    List<Button> btnList = new ArrayList<>();
    public XingIdiom xingIdiom;

    public PatternFragment() {
        // Required empty public constructor
    }


    public static PatternFragment newInstance() {
        PatternFragment fragment = new PatternFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pattern, container, false);

        init(view);
        setContent();
        TextView textView = view.findViewById(R.id.text_explain);
        LinearLayout linearLayout = view.findViewById(R.id.next_btn);

        for(Button btn : btnList){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(btn.getText() == xingIdiom.getCorrect()){
                        btn.setBackground(getResources().getDrawable(R.drawable.background_green));
                        btn.setTextColor(getResources().getColor(R.color.white));
                        if(textView.getText() == ""){
                            textView.setText("回答正确！");
                        }
                    }
                    else{
                        btn.setBackground(getResources().getDrawable(R.drawable.background_red));
                        btn.setTextColor(getResources().getColor(R.color.white));
                        if(textView.getText() == ""){
                            textView.setText("回答错误！");
                        }
                    }
                }
            });
        }


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContent();
            }
        });

        return view;
    }

    public void init(View view){
        btnList.add(view.findViewById(R.id.btn1));
        btnList.add(view.findViewById(R.id.btn2));
        btnList.add(view.findViewById(R.id.btn3));
        btnList.add(view.findViewById(R.id.btn4));
        for (Button btn: btnList) {
            btn.setTextColor(getResources().getColor(R.color.black));
            btn.setBackground(getResources().getDrawable(R.drawable.white_background));
        }
    }

    public void setContent(){
        PatternTask task = new PatternTask((MainActivity) getActivity());
        task.execute();
    }

    private static class PatternTask extends AsyncTask<Void, Void, String> {

        public static final String URL = "http://47.113.102.111:8080/getOneRandomXingIdiom";
        private final WeakReference<MainActivity> activityReference;

        public PatternTask  (MainActivity context) {
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
            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e("PatternFragment","Activity弱引用创建失败或Activity已经结束");
                return;
            }
            JSONObject data = JSON.parseObject(str,JSONObject.class);
            ViewPager viewpager = activity.findViewById(R.id.view_pager1);
            PatternFragment fragment = (PatternFragment) viewpager.getAdapter().instantiateItem(viewpager, 0);

            fragment.xingIdiom = (XingIdiom) data.get("idiom");
            String content = data.getString("content");
            List<String> list = JSONObject.parseArray(content,String.class);

            System.out.println(list);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}