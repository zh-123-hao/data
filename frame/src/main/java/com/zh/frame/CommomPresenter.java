package com.zh.frame;

import java.lang.ref.SoftReference;

public class CommomPresenter<V extends ICommonView,M extends ICommonModel> implements ICommonPreseneter {
    private SoftReference<V> mView;
    private SoftReference<M> mMdel;

    public CommomPresenter(ICommonView view, ICommonModel model) {
        mView = (SoftReference<V>) new SoftReference<>(view);
        mMdel = (SoftReference<M>) new SoftReference<>(model);
    }

    @Override
    public void getData(int whichApi, Object... pPs) {
        if(mMdel!=null && mMdel.get() !=null){
            mMdel.get().getData(this, whichApi, pPs);
        }
    }

    @Override
    public void onSuccess(int whichApi, Object... pD) {
        if (mView!=null && mView.get()!=null){
            mView.get().onSuccess(whichApi, pD);
        }
    }

    @Override
    public void onFailed(int whichApi, Throwable pThrowable) {
        if (mView!=null&& mView.get()!=null){
            mView.get().onFailed(whichApi, pThrowable);
        }
    }

    public void clear(){
        if (mView!=null){
            mView.clear();
            mView = null;
        }
        if (mMdel != null) {
            mMdel.clear();
            mMdel = null;
        }
    }
}
