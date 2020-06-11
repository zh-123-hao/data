package com.zh.project_mvp.view;

import android.content.Intent;

import android.text.TextUtils;
import android.view.View;

import com.zh.data.BaseInfo;
import com.zh.data.LoginInfo;
import com.zh.data.PersonHeader;
import com.zh.frame.ApiConfig;
import com.zh.frame.constants.ConstantKey;
import com.zh.project_mvp.R;
import com.zh.project_mvp.base.BaseMvpActivity;
import com.zh.project_mvp.model.AccountModel;
import com.zh.utils.utils.newAdd.SharedPrefrenceUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.zh.project_mvp.JumpConstant.*;

public class LoginActivity extends BaseMvpActivity<AccountModel> implements LoginView.LoginViewCallBack {
    @BindView(R.id.login_view)
    LoginView mLoginView;
    private String phone;
    private Disposable mSubscribe;
    private String mFromType;

    @Override
    public void setUpData() {

    }

    @Override
    public void setUpView() {
        mFromType = getIntent().getStringExtra(JUMP_KEY);
        mLoginView.setLoginViewCallBack(this);
    }

    @Override
    public AccountModel setModel() {
        return new AccountModel();
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi){
            case ApiConfig.SEND_VERIFY :
                BaseInfo<String> info = (BaseInfo<String>) pD[0];
                showToast(info.result);
                goTime();
                break;
            case ApiConfig.VERIFY_LOGIN :
                BaseInfo<LoginInfo>baseInfo = (BaseInfo<LoginInfo>) pD[0];
                LoginInfo loginInfo = baseInfo.result;
                loginInfo.login_name = phone;
                mApplication.setLoginInfo(loginInfo);
                presenter.getData(ApiConfig.GET_HEADER_INFO);
                break;
            case ApiConfig.GET_HEADER_INFO :
                PersonHeader personHeader = ((BaseInfo<PersonHeader>) pD[0]).result;
                mApplication.getLoginInfo().personHeader = personHeader;
                SharedPrefrenceUtils.putObject(this, ConstantKey.LOGIN_INFO, mApplication.getLoginInfo());
                jump();
                break;
        }
    }
    @OnClick({R.id.close_login, R.id.register_press, R.id.forgot_pwd, R.id.login_by_qq, R.id.login_by_wx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close_login:
                if (!TextUtils.isEmpty(mFromType)&&(mFromType.equals(SUB_TO_LOGIN) || mFromType.equals(SPLASH_TO_LOGIN) || mFromType.equals(REGISTER_TO_LOGIN))){
                    startActivity(new Intent(this,HomeActivity.class));
                }
                break;
            case R.id.register_press:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.forgot_pwd:
                break;
            case R.id.login_by_qq:
                break;
            case R.id.login_by_wx:
                break;
        }
    }
    private void jump() {
        if (mFromType.equals(SPLASH_TO_LOGIN) ||  mFromType.equals(SUB_TO_LOGIN)){
            startActivity(new Intent(this,HomeActivity.class));
        }
        finish();
    }
    private long time = 60l;
    private void goTime() {
        mSubscribe = Observable.interval(1, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(goTime -> {
            mLoginView.getVerifyCode.setText(time - goTime + "s");
            if (time - goTime < 1) {
                doPre();
                mLoginView.getVerifyCode.setText("获取验证码");
            }
        });
    }
    private void doPre() {
        if (mSubscribe != null && !mSubscribe.isDisposed()) mSubscribe.dispose();
        mLoginView.getVerifyCode.setText("获取验证码");
    }
    @Override
    public void sendVerifyCode(String phoneNum) {
        this.phone = phoneNum;
        presenter.getData(ApiConfig.SEND_VERIFY, phone);
    }

    @Override
    public void loginPress(int type, String userName, String pwd) {
        doPre();
        if (mLoginView.mCurrentLoginType == mLoginView.VERIFY_TYPE){
            presenter.getData(ApiConfig.VERIFY_LOGIN, userName,pwd);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        doPre();
    }
}
