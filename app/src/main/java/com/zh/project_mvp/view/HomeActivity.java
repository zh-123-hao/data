package com.zh.project_mvp.view;

import androidx.navigation.Navigation;

import com.zh.project_mvp.R;
import com.zh.project_mvp.base.BaseMvpActivity;
import com.zh.project_mvp.model.CommomModel;

public class HomeActivity extends BaseMvpActivity<CommomModel> {


    @Override
    public void setUpData() {

    }

    @Override
    public void setUpView() {
        Navigation.findNavController(this,R.id.project_fragment_control);
    }

    @Override
    public CommomModel setModel() {
        return new CommomModel();
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {

    }
}
