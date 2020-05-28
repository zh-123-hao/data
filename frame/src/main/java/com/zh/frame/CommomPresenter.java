package com.zh.frame;

public class CommomPresenter implements ICommonPreseneter {
    private ICommonView view;
    private ICommonModel model;

    public CommomPresenter(ICommonView view, ICommonModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void getData(int whichApi, Object... pPs) {
        model.getData(this, whichApi, pPs);
    }

    @Override
    public void onSuccess(int whichApi, int loadType, Object... pD) {
        view.onSuccess(whichApi, loadType, pD);
    }

    @Override
    public void onFailed(int whichApi, Throwable pThrowable) {
        view.onFailed(whichApi, pThrowable);
    }
}
