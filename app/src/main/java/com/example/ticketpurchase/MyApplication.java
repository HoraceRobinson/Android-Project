package com.example.ticketpurchase;

import android.app.Application;

import com.xuexiang.xui.XUI;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        XUI.init(this);
        XUI.debug(true);
        //设置默认字体为华文行楷，这里写你的字体库
//        XUI.initFontStyle("fonts/simfang.ttf");
    }
}
