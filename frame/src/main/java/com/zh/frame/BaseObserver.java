package com.zh.frame;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver implements Observer {
    private Disposable disposable;
    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    @Override
    public void onNext(Object o) {
        onSuccess(o);
        dispose();
    }

    @Override
    public void onError(Throwable e) {
        onFailed(e);
        dispose();
    }

    @Override
    public void onComplete() {
        dispose();
    }

    public abstract void onSuccess(Object value);

    public abstract void onFailed(Throwable e);

    public void dispose (){
        if (disposable!=null && !disposable.isDisposed()){
            disposable.isDisposed();
        }
    }
}
