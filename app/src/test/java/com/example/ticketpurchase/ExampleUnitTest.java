package com.example.ticketpurchase;

import org.junit.Test;

import static org.junit.Assert.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.ticketpurchase.room.Idiom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void test1() {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String tmp;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL("http://47.113.102.111:8080/findIdiomByName/"+ URLEncoder.encode("一壶千金", "UTF-8"));
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
        System.out.println(res);
    }
}