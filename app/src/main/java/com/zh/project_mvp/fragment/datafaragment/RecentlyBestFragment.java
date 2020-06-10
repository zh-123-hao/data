package com.zh.project_mvp.fragment.datafaragment;

import com.zh.project_mvp.R;
import com.zh.project_mvp.base.BaseMvpFragment;
import com.zh.project_mvp.model.DataModel;

public class RecentlyBestFragment extends BaseMvpFragment<DataModel> {
    public static RecentlyBestFragment newInstance() {
        RecentlyBestFragment fragment = new RecentlyBestFragment();
        return fragment;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.refresh_list_layout;
    }

    @Override
    public DataModel setModel() {
        return null;
    }

    @Override
    public void setUpView() {

    }

    @Override
    public void setUpData() {

    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {

    }
}
