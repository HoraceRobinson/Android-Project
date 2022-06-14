package com.example.ticketpurchase.home.fragment.function;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.ticketpurchase.R;
import com.example.ticketpurchase.home.activity.MainActivity;
import com.example.ticketpurchase.room.YiIdiom;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeaningFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeaningFragment extends Fragment {

    public List<TextView> textList = new ArrayList<>();
    public List<Button> btnList = new ArrayList<>();
    public TextView textView;


    public MeaningFragment() {
        // Required empty public constructor
    }

    public static MeaningFragment newInstance() {
        MeaningFragment fragment = new MeaningFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pronunce, container, false);

        init(view);
        setContent();

        LinearLayout linearLayout = view.findViewById(R.id.next_btn);

        textView = view.findViewById(R.id.text_explain);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContent();
            }
        });

        return view;
    }

    private void init(View view) {
        textList.add(view.findViewById(R.id.text1));
        textList.add(view.findViewById(R.id.text2));
        textList.add(view.findViewById(R.id.text3));
        textList.add(view.findViewById(R.id.text4));
        btnList.add(view.findViewById(R.id.btn1));
        btnList.add(view.findViewById(R.id.btn2));
        btnList.add(view.findViewById(R.id.btn3));
        btnList.add(view.findViewById(R.id.btn4));
        textView = view.findViewById(R.id.text_explain);
        for (Button btn: btnList) {
            btn.setTextColor(getResources().getColor(R.color.black));
            btn.setBackground(getResources().getDrawable(R.drawable.white_background));
        }
    }

    private void setContent() {
        for (Button btn: btnList) {
            btn.setTextColor(getResources().getColor(R.color.black));
            btn.setBackground(getResources().getDrawable(R.drawable.white_background));
        }
        for(TextView text : textList){
            text.setText("");
        }
        textView.setText("");
        MeaningFragment.MeaningTask task = new MeaningFragment.MeaningTask((MainActivity) getActivity());
        task.execute();
    }

    private static class MeaningTask extends AsyncTask<Void, Void, String> {

        public static final String URL = "http://47.113.102.111:8080/getOneRandomYiIdiom";
        private final WeakReference<MainActivity> activityReference;

        public MeaningTask (MainActivity context) {
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
                Log.e("MeaningFragment","Activity弱引用创建失败或Activity已经结束");
                return;
            }
            ViewPager viewpager = activity.findViewById(R.id.view_pager1);
            MeaningFragment fragment = (MeaningFragment) viewpager.getAdapter().instantiateItem(viewpager, 2);

            JSONObject data = JSON.parseObject(str);
            YiIdiom yiIdiom = JSON.parseObject(data.getString("idiom"),YiIdiom.class);
            List<String> list = JSONObject.parseArray(data.getString("content"),String.class);
            for (int i = 0; i < 4; i++) {
                fragment.btnList.get(i).setText(list.get(i));
            }
            for (int i = 0; i < 4; i++) {
                fragment.textList.get(i).setText("" + yiIdiom.getIdiom().charAt(i));
            }

            for(Button btn : fragment.btnList){
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(btn.getText().toString().equals(yiIdiom.getCorrect())){
                            btn.setBackground(activity.getResources().getDrawable(R.drawable.background_green));
                            btn.setTextColor(activity.getResources().getColor(R.color.white));
                            if(fragment.textView.getText() == ""){
                                fragment.textView.setText("回答正确！");
                            }
                        }
                        else{
                            btn.setBackground(activity.getResources().getDrawable(R.drawable.background_red));
                            btn.setTextColor(activity.getResources().getColor(R.color.white));
                            if(fragment.textView.getText() == ""){
                                fragment.textView.setText("回答错误！");
                            }
                        }
                    }
                });
            }
        }
    }
}