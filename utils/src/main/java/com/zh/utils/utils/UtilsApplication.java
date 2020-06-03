package com.zh.utils.utils;

import android.app.Application;
import android.content.Context;

public class UtilsApplication extends Application {
    private static UtilsApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public static Context getUtilsApplicaitonContext(){
        return mApplication.getApplicationContext();
    }
}
