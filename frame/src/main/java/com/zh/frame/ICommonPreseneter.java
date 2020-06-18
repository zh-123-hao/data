package com.zh.frame;

import io.reactivex.disposables.Disposable;

public interface ICommonPreseneter<P> extends ICommonView {
    //由于他作为中间层发起网络连接请求，将请求的结果返回到view层
    void getData(int whichApi,P...pPs);

    void addObserver(Disposable pDisposable);
}
