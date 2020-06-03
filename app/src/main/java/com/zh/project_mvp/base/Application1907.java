package com.zh.project_mvp.base;

import android.content.Context;

import com.zh.frame.FrameApplication;

/**
 * Created by 任小龙 on 2020/5/29.
 */
public class Application1907 extends FrameApplication {
    private static Application1907 mApplication1907;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication1907 = this;
    }

    public Application1907 getApplication(){
        return mApplication1907;
    }

    public static Context get07ApplicationContext(){
        return mApplication1907.getApplicationContext();
    }
}
