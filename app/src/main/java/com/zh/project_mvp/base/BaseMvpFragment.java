package com.zh.project_mvp.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zh.frame.CommomPresenter;
import com.zh.frame.ICommonModel;
import com.zh.frame.ICommonView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseMvpFragment<M extends ICommonModel> extends BaseFragment implements ICommonView {
    private M model;
    public CommomPresenter persenter;
    private Unbinder mBind;
    private boolean isInit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setLayoutId(), null);
        mBind = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpView();
        if (model == null)model = setModel();
        if (persenter == null)persenter = new CommomPresenter(this, model);
        if (!isInit){
            setUpData();
            isInit = true;
        }
    }

    protected abstract int setLayoutId();

    public abstract M setModel();

    public abstract void setUpView();

    public abstract void setUpData();

    public abstract void netSuccess(int whichApi, Object[] pD);

    public void netFailed(int whichApi, Throwable pThrowable){}

    @Override
    public void onSuccess(int whichApi, Object[] pD) {
        netSuccess(whichApi, pD);
    }

    @Override
    public void onFailed(int whichApi, Throwable pThrowable) {
        showLog("net work error: " + whichApi + "error content" + pThrowable != null && !TextUtils.isEmpty(pThrowable.getMessage()) ? pThrowable.getMessage() : "不明错误类型");
        netFailed(whichApi, pThrowable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        persenter.clear();
        if (mBind != null)mBind.unbind();
    }
}
