package com.zh.frame;

import android.app.Activity;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class CommomPresenter<V extends ICommonView,M extends ICommonModel> implements ICommonPreseneter {
    private SoftReference<V> mView;
    private SoftReference<M> mModel;
    private List<Disposable> mDisposableList;
    private LoadView mInstance;
    private WeakReference<Activity> mActivityWeakReference;

    public CommomPresenter(ICommonView view, ICommonModel model) {
        mView = (SoftReference<V>) new SoftReference<>(view);
        mModel = (SoftReference<M>) new SoftReference<>(model);
        mDisposableList = new ArrayList<>();
    }
    public void allowLoading(Activity pActivity) {
        mActivityWeakReference = new WeakReference<>(pActivity);
    }
    @Override
    public void getData(int whichApi, Object... pPs) {
        if (mActivityWeakReference != null && mActivityWeakReference.get() != null) {
            mInstance = LoadView.getInstance(mActivityWeakReference.get(), null);
            int load = -1;
            if (pPs != null && pPs.length != 0 && pPs[0] instanceof Integer){
                load = (int) pPs[0];
            }
            if (load != LoadTypeConfig.MORE && load != LoadTypeConfig.REFRESH && !mInstance.isShowing()){
                mInstance.show();
            }
        }

        if (mModel != null && mModel.get() != null) mModel.get().getData(this, whichApi, pPs);
    }

    @Override
    public void onSuccess(int whichApi, Object... pD) {
        if (mView != null && mView.get() != null) mView.get().onSuccess(whichApi, pD);
        if (mInstance != null && mInstance.isShowing())mInstance.dismiss();
    }

    @Override
    public void onFailed(int whichApi, Throwable pThrowable) {
        if (mView != null && mView.get() != null) mView.get().onFailed(whichApi, pThrowable);
        if (mInstance != null && mInstance.isShowing())mInstance.dismiss();
    }

    public void clear(){
        if (mView!=null){
            mView.clear();
            mView = null;
        }
        if (mModel != null) {
            mModel.clear();
            mModel = null;
        }
    }
    @Override
    public void addObserver(Disposable pDisposable) {
        if (mDisposableList == null) return;
        mDisposableList.add(pDisposable);
    }
}
