package com.zh.project_mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.zh.frame.CommomPresenter;
import com.zh.frame.ICommonModel;
import com.zh.frame.ICommonView;

import butterknife.ButterKnife;

public abstract class BaseMvpActivity<M extends ICommonModel> extends BaseActivity implements ICommonView {
    private M mModel;
    public CommomPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        ButterKnife.bind(this);
        mModel = setModel();
        presenter = new CommomPresenter(this, mModel);
        setUpView();
        setUpData();
    }

    public abstract void setUpData();

    public abstract void setUpView();

    public abstract M setModel();

    public abstract int getLayoutID();

    public abstract void netSuccess(int whichApi, Object[] pD);

    public void netFailed(int whichApi, Throwable pThrowable){

    }

    @Override
    public void onSuccess(int whichApi,  Object[] pD) {
        netSuccess(whichApi, pD);
    }

    @Override
    public void onFailed(int whichApi, Throwable pThrowable) {
        showLog("net work error: "+whichApi+"error content"+ pThrowable != null && !TextUtils.isEmpty(pThrowable.getMessage()) ? pThrowable.getMessage() : "不明错误类型");
        netFailed(whichApi,pThrowable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.clear();
    }
}
