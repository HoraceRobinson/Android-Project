package com.example.ticketpurchase.room;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.ticketpurchase.CollectedIdiomActivity;
import com.example.ticketpurchase.IdiomContentActivity;
import com.example.ticketpurchase.MainActivity;
import com.example.ticketpurchase.MyAdapter;
import com.example.ticketpurchase.MyPopupWindow;
import com.example.ticketpurchase.R;
import com.xuexiang.xui.widget.popupwindow.popup.XUIPopup;

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
import java.util.List;

public class DBEngine {

    private IdiomDao idiomDao;

    public DBEngine(Context context) {
        IdiomDatabase idiomDatabase = IdiomDatabase.getInstance(context);
        idiomDao = idiomDatabase.getIdiomDao();
    }

    public IdiomDao getIdiomDao() {
        return idiomDao;
    }

    public void insertIdioms(Idiom...idioms) {
        new InsertAsyncTask(idiomDao).execute(idioms);
    }

    static class InsertAsyncTask extends AsyncTask<Idiom,Void,Void> {
        private IdiomDao dao;
        public InsertAsyncTask(IdiomDao idiomDao) {
            dao = idiomDao;
        }
        @Override
        protected Void doInBackground(Idiom ...idioms) {
            dao.insertIdioms(idioms);
            return null;
        }
    }

    public void deleteIdioms(Idiom ...idioms) {
        new DeleteAsyncTask(idiomDao).execute(idioms);
    }

    static class DeleteAsyncTask extends AsyncTask<Idiom,Void,Void> {
        private IdiomDao dao;
        public DeleteAsyncTask(IdiomDao idiomDao) {
            dao = idiomDao;
        }
        @Override
        protected Void doInBackground(Idiom ...idioms) {
            dao.deleteIdioms(idioms);
            return null;
        }
    }

    public void getAllIdioms(CollectedIdiomActivity context) {
        new GetAllAsyncTask(idiomDao, context).execute();
    }

    static class GetAllAsyncTask extends AsyncTask<Void, Void, List<String>> {

        private IdiomDao dao;
        private final WeakReference<CollectedIdiomActivity> activityReference;

        public GetAllAsyncTask(IdiomDao idiomDao, CollectedIdiomActivity context) {
            dao = idiomDao;
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<String> doInBackground(Void ...voids) {
            return dao.getAllIdioms();
        }

        @Override
        protected void onPostExecute(List<String> idioms) {
            super.onPostExecute(idioms);
            CollectedIdiomActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e("CollectedIdiomActivity","Activity弱引用创建失败或Activity已经结束");
            }
            else {
                RecyclerView recyclerView = activity.findViewById(R.id.recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                recyclerView.setAdapter(new MyAdapter(idioms,activity));
            }
        }
    }

    public void popupIdiom(String idiom, MainActivity context) {
        new PopupIdiomAsyncTask(idiomDao, context).execute(idiom);
    }

    static class PopupIdiomAsyncTask extends AsyncTask<String, Void, Idiom> {

        private IdiomDao dao;
        private final WeakReference<MainActivity> activityReference;
        private Boolean collected;

        public PopupIdiomAsyncTask(IdiomDao idiomDao, MainActivity context) {
            dao = idiomDao;
            activityReference = new WeakReference<>(context);
            this.collected = false;
        }

        @Override
        protected Idiom doInBackground(String ...idioms) {
            String arg = idioms[0];
            Idiom idiom = dao.getTheIdiom(arg);
            if (idiom == null) {
//                网络请求
                InputStream inputStream = null;
                InputStreamReader inputStreamReader = null;
                BufferedReader bufferedReader = null;
                String tmp;
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    URL url = new URL("http://47.113.102.111:8080/findIdiomByName/"+ URLEncoder.encode(arg, "UTF-8"));
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
                JSONObject data = JSON.parseObject(stringBuilder.toString());
                idiom = new Idiom(data.getString("idiom"), data.getString("pinyin"), data.getString("meaning"), data.getString("reference"), data.getString("example"));
            }
            else {
                collected = true;
            }
            return idiom;
        }

        @Override
        protected void onPostExecute(Idiom idiom) {
            super.onPostExecute(idiom);
            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e("MainActivity","Activity弱引用创建失败或Activity已经结束");
            }
            else {
                MyPopupWindow window = new MyPopupWindow(activity);
                window.setIdiom(idiom.getIdiom());
                window.setExplanation(idiom.getMeaning());
                if (collected) {
                    window.setButtonBackground(true);
                }
                else {
                    window.setButtonBackground(false);
                }
                activity.myPopup.setContentView(window);
                activity.myPopup.setAnimStyle(XUIPopup.ANIM_GROW_FROM_CENTER);
                activity.myPopup.setPreferredDirection(XUIPopup.DIRECTION_TOP);
                activity.myPopup.show(activity.findViewById(R.id.textView));
            }
        }
    }

    public void collectIdiom(String idiom, MainActivity context) {
        new CollectIdiomAsyncTask(idiomDao, context).execute(idiom);
    }

    static class CollectIdiomAsyncTask extends AsyncTask<String, Void, Void> {

        private IdiomDao dao;
        private final WeakReference<MainActivity> activityReference;
        private Boolean collected = true;

        public CollectIdiomAsyncTask(IdiomDao idiomDao, MainActivity context) {
            dao = idiomDao;
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected Void doInBackground(String ...idioms) {
            Idiom idiom = dao.getTheIdiom(idioms[0]);
            if (idiom == null) {
//                网络请求获取idiom
                InputStream inputStream = null;
                InputStreamReader inputStreamReader = null;
                BufferedReader bufferedReader = null;
                String tmp;
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    URL url = new URL("http://47.113.102.111:8080/findIdiomByName/"+ URLEncoder.encode(idioms[0], "UTF-8"));
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
                JSONObject data = JSON.parseObject(stringBuilder.toString());
                Idiom res = new Idiom(data.getString("idiom"), data.getString("pinyin"), data.getString("meaning"), data.getString("reference"), data.getString("example"));
                dao.insertIdioms(res);
            }
            else {
                dao.deleteIdioms(idiom);
                collected = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e("MainActivity","Activity弱引用创建失败或Activity已经结束");
            }
            else {
                if (collected) {
                    Toast.makeText(activity,"成语已经被收藏",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(activity,"成语已经移出收藏",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void getContentIdiom(String idiom, IdiomContentActivity context) {
        new GetContentAsyncTask(idiomDao, context).execute(idiom);
    }

    static class GetContentAsyncTask extends AsyncTask<String, Void, Idiom> {

        private IdiomDao dao;
        private final WeakReference<IdiomContentActivity> activityReference;

        public GetContentAsyncTask(IdiomDao idiomDao, IdiomContentActivity context) {
            dao = idiomDao;
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected Idiom doInBackground(String ...idioms) {
            Idiom idiom = dao.getTheIdiom(idioms[0]);
            return idiom;
        }

        @Override
        protected void onPostExecute(Idiom idiom) {
            super.onPostExecute(idiom);
            IdiomContentActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e("IdiomContentActivity","Activity弱引用创建失败或Activity已经结束");
            }
            else {
                TextView textView1 = activity.findViewById(R.id.idiom);
                textView1.setText(idiom.getIdiom());
                TextView textView2 = activity.findViewById(R.id.pinyin);
                textView2.setText(idiom.getPinyin());
                TextView textView3 = activity.findViewById(R.id.meaning);
                textView3.setText(idiom.getMeaning());
                TextView textView4 = activity.findViewById(R.id.reference);
                textView4.setText(idiom.getReference());
                TextView textView5 = activity.findViewById(R.id.example);
                textView5.setText(idiom.getExample());
            }
        }
    }

    public void deleteTheIdiom(String idiom) {
        new DeleteTheAsyncTask(idiomDao).execute(idiom);
    }

    static class DeleteTheAsyncTask extends AsyncTask<String, Void, Void> {

        private IdiomDao dao;

        public DeleteTheAsyncTask(IdiomDao idiomDao) {
            dao = idiomDao;
        }

        @Override
        protected Void doInBackground(String ...idioms) {
            dao.deleteTheIdiom(idioms[0]);
            return null;
        }
    }
}
