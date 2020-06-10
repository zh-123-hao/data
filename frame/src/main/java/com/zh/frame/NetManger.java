package com.zh.frame;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class NetManger {
    private NetManger() {
    }

    private static volatile NetManger sNetManger;

    public static NetManger getInstance() {
        if (sNetManger == null) {
            synchronized (NetManger.class) {
                sNetManger = new NetManger();
            }
        }
        return sNetManger;
    }

    public <T> IService getService(T... t) {
        String baseUrl = ServerAddressConfig.BASE_URL;
        if (t != null && t.length != 0) {
            baseUrl = (String) t[0];
        }
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(initClient())
                //.client(new OkHttpClient())
                .build()
                .create(IService.class);
    }

    private OkHttpClient initClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.addInterceptor(new CommonHeadersInterceptor());
        builder.addInterceptor(new CommonParamsInterceptor());
        builder.addInterceptor(initLogInterceptor());
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(15,TimeUnit.SECONDS);
        return builder.build();
    }
    private Interceptor initLogInterceptor(){
        HttpLoggingInterceptor log = new HttpLoggingInterceptor();
        log.setLevel(HttpLoggingInterceptor.Level.BODY);
        return log;
    }
    public <T, O> void netWork(Observable<T> localTestInfo, final ICommonPreseneter pPresenter, final int whichApi, final O... o) {
        localTestInfo.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onSuccess(Object value) {
                        pPresenter.onSuccess(whichApi, value, o != null && o.length == 1 ? o[0] : o);
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        pPresenter.onFailed(whichApi, e);
                    }
                });
    }
}
