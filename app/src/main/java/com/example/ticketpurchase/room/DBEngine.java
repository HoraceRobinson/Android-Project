package com.example.ticketpurchase.room;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.ticketpurchase.ChainActivity;
import com.example.ticketpurchase.IdiomActivity;
import com.example.ticketpurchase.R;
import com.example.ticketpurchase.ReverseChainActivity;
import com.example.ticketpurchase.StarItemActivity;
import com.example.ticketpurchase.adapter.StarAdapter;
import com.example.ticketpurchase.extendview.MyPopupWindow;
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

    public void getAllIdioms(StarItemActivity context) {
        new GetAllAsyncTask(idiomDao, context).execute();
    }

    static class GetAllAsyncTask extends AsyncTask<Void, Void, List<Idiom>> {

        private IdiomDao dao;
        private final WeakReference<StarItemActivity> activityReference;

        public GetAllAsyncTask(IdiomDao idiomDao, StarItemActivity context) {
            dao = idiomDao;
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<Idiom> doInBackground(Void ...voids) {
            return dao.getAllIdioms();
        }

        @Override
        protected void onPostExecute(List<Idiom> idioms) {
            super.onPostExecute(idioms);
            StarItemActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e("CollectedIdiomActivity","Activity弱引用创建失败或Activity已经结束");
            }
            else {
                RecyclerView recyclerView = activity.findViewById(R.id.idiom_list);
                recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                recyclerView.setAdapter(new StarAdapter(idioms,activity));
            }
        }
    }

    public void popupIdiom(String idiom, ChainActivity context) {
        new PopupIdiomAsyncTask(idiomDao, context).execute(idiom);
    }

    static class PopupIdiomAsyncTask extends AsyncTask<String, Void, Idiom> {

        private IdiomDao dao;
        private final WeakReference<ChainActivity> activityReference;
        private Boolean collected;

        public PopupIdiomAsyncTask(IdiomDao idiomDao, ChainActivity context) {
            dao = idiomDao;
            activityReference = new WeakReference<>(context);
            this.collected = false;
        }

        @Override
        protected Idiom doInBackground(String ...idioms) {
            String arg = idioms[0];
            Idiom idiom = dao.getTheIdiom(arg);
            if (idiom == null) {
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
                if (data == null) {
                    return null;
                }
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
            ChainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e("ChainActivity","Activity弱引用创建失败或Activity已经结束");
            }
            else {
                if (idiom == null) {
                    Toast.makeText(activity,"没有找到此成语",Toast.LENGTH_SHORT).show();
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
                    activity.myPopup.show(activity.findViewById(R.id.layout1));
                }

            }
        }
    }

    public void collectIdiom(String idiom, ChainActivity context) {
        new CollectIdiomAsyncTask(idiomDao, context).execute(idiom);
    }

    static class CollectIdiomAsyncTask extends AsyncTask<String, Void, Void> {

        private IdiomDao dao;
        private final WeakReference<ChainActivity> activityReference;
        private Boolean collected = true;

        public CollectIdiomAsyncTask(IdiomDao idiomDao, ChainActivity context) {
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
            ChainActivity activity = activityReference.get();
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

    public void popupReverseIdiom(String idiom, ReverseChainActivity context) {
        new PopupReverseIdiomAsyncTask(idiomDao, context).execute(idiom);
    }

    static class PopupReverseIdiomAsyncTask extends AsyncTask<String, Void, Idiom> {

        private IdiomDao dao;
        private final WeakReference<ReverseChainActivity> activityReference;
        private Boolean collected;

        public PopupReverseIdiomAsyncTask(IdiomDao idiomDao, ReverseChainActivity context) {
            dao = idiomDao;
            activityReference = new WeakReference<>(context);
            this.collected = false;
        }

        @Override
        protected Idiom doInBackground(String ...idioms) {
            String arg = idioms[0];
            Idiom idiom = dao.getTheIdiom(arg);
            if (idiom == null) {
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
                if (data == null) {
                    return null;
                }
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
            ReverseChainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e("ReverseChainActivity","Activity弱引用创建失败或Activity已经结束");
            }
            else {
                if (idiom == null) {
                    Toast.makeText(activity,"没有找到此成语",Toast.LENGTH_SHORT).show();
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
                    activity.myPopup.show(activity.findViewById(R.id.layout1));
                }

            }
        }
    }

    public void collectReverseIdiom(String idiom, ReverseChainActivity context) {
        new CollectReverseIdiomAsyncTask(idiomDao, context).execute(idiom);
    }

    static class CollectReverseIdiomAsyncTask extends AsyncTask<String, Void, Void> {

        private IdiomDao dao;
        private final WeakReference<ReverseChainActivity> activityReference;
        private Boolean collected = true;

        public CollectReverseIdiomAsyncTask(IdiomDao idiomDao, ReverseChainActivity context) {
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
            ReverseChainActivity activity = activityReference.get();
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

    public void getContentIdiom(String idiom, IdiomActivity context) {
        new GetContentAsyncTask(idiomDao, context).execute(idiom);
    }

    static class GetContentAsyncTask extends AsyncTask<String, Void, Idiom> {

        private IdiomDao dao;
        private final WeakReference<IdiomActivity> activityReference;

        public GetContentAsyncTask(IdiomDao idiomDao, IdiomActivity context) {
            dao = idiomDao;
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected Idiom doInBackground(String ...idioms) {
            Idiom idiom = dao.getTheIdiom(idioms[0]);
            if (idiom == null) {
                idiom = new Idiom(idioms[0],null,null,null,null);
            }
            return idiom;
        }

        @Override
        protected void onPostExecute(Idiom idiom) {
            super.onPostExecute(idiom);
            IdiomActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e("IdiomContentActivity","Activity弱引用创建失败或Activity已经结束");
            }
            else {
                if (idiom.getPinyin() == null) {
                    activity.isStarred = false;
                    SearchAsyncTask task = new SearchAsyncTask(activity);
                    task.execute(idiom.getIdiom());
                    activity.star.setImageDrawable(activity.getDrawable(R.drawable.ic_star));
                }
                else {
                    activity.isStarred = true;
                    activity.textView1.setText(((Character) idiom.getIdiom().charAt(0)).toString());
                    activity.textView2.setText(((Character) idiom.getIdiom().charAt(1)).toString());
                    activity.textView3.setText(((Character) idiom.getIdiom().charAt(2)).toString());
                    activity.textView4.setText(((Character) idiom.getIdiom().charAt(3)).toString());
                    activity.pinyin.setText(idiom.getPinyin());
                    activity.meaning.setText(idiom.getMeaning());
                    activity.reference.setText(idiom.getReference());
                    activity.example.setText(idiom.getExample());
                    activity.star.setImageDrawable(activity.getDrawable(R.drawable.ic_star_fill));
                }
            }
        }
    }

    static class SearchAsyncTask extends AsyncTask<String, Void, Idiom> {

        private final WeakReference<IdiomActivity> activityReference;
        private Boolean collected;

        public SearchAsyncTask(IdiomActivity context) {
            activityReference = new WeakReference<>(context);
            this.collected = false;
        }

        @Override
        protected Idiom doInBackground(String ...idioms) {
            String arg = idioms[0];
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
            Idiom idiom = new Idiom(data.getString("idiom"), data.getString("pinyin"), data.getString("meaning"), data.getString("reference"), data.getString("example"));
            return idiom;
        }

        @Override
        protected void onPostExecute(Idiom idiom) {
            super.onPostExecute(idiom);
            IdiomActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e("IdiomActivity","Activity弱引用创建失败或Activity已经结束");
            }
            else {
                activity.textView1.setText(((Character) idiom.getIdiom().charAt(0)).toString());
                activity.textView2.setText(((Character) idiom.getIdiom().charAt(1)).toString());
                activity.textView3.setText(((Character) idiom.getIdiom().charAt(2)).toString());
                activity.textView4.setText(((Character) idiom.getIdiom().charAt(3)).toString());
                activity.pinyin.setText(idiom.getPinyin());
                activity.meaning.setText(idiom.getMeaning());
                activity.reference.setText(idiom.getReference());
                activity.example.setText(idiom.getExample());
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
