package com.example.socket;

import android.app.Application;
import android.os.SystemClock;

public class MyApp extends Application {

    public Application getInstance(){
        return this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(3000);
    }
}
