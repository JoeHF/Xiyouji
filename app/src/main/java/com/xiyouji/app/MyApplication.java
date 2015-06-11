package com.xiyouji.app;

import android.app.Application;

import com.baidu.frontia.FrontiaApplication;
import com.baidu.mapapi.SDKInitializer;

public class MyApplication extends FrontiaApplication {
    public static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        application = this;
    }
}
