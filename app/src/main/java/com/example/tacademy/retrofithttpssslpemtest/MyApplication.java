package com.example.tacademy.retrofithttpssslpemtest;

import android.app.Application;

/**
 * Created by Tacademy on 2017-02-20.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 보안 통신 초기화
        NetSSL.getInstance().launch(getApplicationContext());
    }
}