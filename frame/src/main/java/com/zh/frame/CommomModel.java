package com.zh.frame;

import com.zh.data.TestBean;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommomModel implements ICommonModel {
    @Override
    public void getData(final ICommonPreseneter preseneter, final int whichApi, Object[] params) {
        final int loaType = (int) params[0];
        Map param = (Map) params[1];
        int pageId = (int) params[2];


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://static.owspace.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Observable<TestBean> data = retrofit.create(IService.class).getData(param, pageId);
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TestBean>() {
                    @Override
                    public void accept(TestBean testBean) throws Exception {
                        preseneter.onSuccess(whichApi, loaType, testBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        preseneter.onFailed(whichApi, throwable);
                    }
                });
    }
}
