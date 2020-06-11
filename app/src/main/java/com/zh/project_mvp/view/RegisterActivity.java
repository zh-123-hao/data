package com.zh.project_mvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zh.basepopo.design.DialogPopup;
import com.zh.data.BaseInfo;
import com.zh.frame.ApiConfig;
import com.zh.project_mvp.R;
import com.zh.project_mvp.base.BaseMvpActivity;
import com.zh.project_mvp.model.AccountModel;
import com.zh.utils.utils.newAdd.RegexUtil;
import com.zh.utils.utils.newAdd.SoftInputControl;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterActivity extends BaseMvpActivity<AccountModel> implements DialogPopup.DialogClickCallBack {

    @BindView(R.id.back_image)
    ImageView back_image;
    @BindView(R.id.title_content)
    TextView title_content;
    @BindView(R.id.right_image)
    ImageView right_image;
    @BindView(R.id.more_content)
    TextView more_content;
    @BindView(R.id.telephone_desc)
    TextView telephone_desc;
    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.cutLine_telephone)
    View cutLine_telephone;
    @BindView(R.id.getVerification)
    TextView getVerification;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.verificationArea)
    ConstraintLayout verificationArea;
    @BindView(R.id.next_page)
    TextView next_page;
    private Disposable mTimeObserver;
    private DialogPopup mConfirmDialog;


    @Override
    public void setUpData() {

    }

    @Override
    public void setUpView() {
        title_content.setText("填写手机号");
        next_page.setEnabled(false);
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                next_page.setEnabled(s.length() == 6 ? true : false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public AccountModel setModel() {
        return new AccountModel();
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_register;
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi){
            case ApiConfig.CHECK_PHONE_IS_USED :
                BaseInfo chechInfo = (BaseInfo) pD[0];
                if (chechInfo.isSuccess()){
                    mConfirmDialog = new DialogPopup(this, true);
                    mConfirmDialog.setContent(userName.getText().toString()+"\n"+"是否将短信发送到该手机");
                    mConfirmDialog.setDialogClickCallBack(this);
                    mConfirmDialog.showPopupWindow();
                }else {
                    showToast("该手机已注册");
                }
                break;
            case ApiConfig.SEND_REGISTER_VERIFY:
                BaseInfo sendResult = (BaseInfo) pD[0];
                if (sendResult.isSuccess()){
                    showToast("验证码发送成功");
                    goTime();
                }else showLog(sendResult.msg);
                break;
            case ApiConfig.REGISTER_PHONE:
                BaseInfo info = (BaseInfo) pD[0];
                if (info.isSuccess()){
                    showToast("验证码验证成功");
                    startActivity(new Intent(this,RegisterPhoneActivity.class).putExtra("phoneNum",telephone_desc.getText().toString() + userName.getText().toString().trim()));
                    finish();
                }else showLog(info.msg);
                break;
        }
    }


    @OnClick({R.id.back_image, R.id.getVerification,R.id.next_page})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_image:
                //TODO: add click handling
                finish();
                break;
            case R.id.getVerification:
                //TODO: add click handling
                if (userName.getText() == null){
                    showToast("请输入手机号");
                    return;
                }
                boolean isPhone = RegexUtil.isPhone(userName.getText().toString().trim());
                if (isPhone){
                    SoftInputControl.hideSoftInput(this, userName);
                    presenter.getData(ApiConfig.CHECK_PHONE_IS_USED, telephone_desc.getText().toString()+userName.getText().toString().trim());

                }else showToast("手机号错误");
                break;
            case R.id.next_page:
                //TODO: add click handling
                presenter.getData(ApiConfig.REGISTER_PHONE, telephone_desc.getText().toString()+userName.getText().toString().trim(), password.getText().toString().trim());
                break;
        }
    }

    private int preTime = 60;
    private void goTime(){
        mTimeObserver = Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(l -> {
                    if (preTime - l > 0) {
                        getVerification.setText(preTime - l + "s");
                    } else {
                        getVerification.setText("获取验证码");
                        disPose();
                    }
                });
    }

    private void disPose() {
        if (mTimeObserver != null && !mTimeObserver.isDisposed())mTimeObserver.dispose();
    }

    @Override
    protected void onStop() {
        super.onStop();
        disPose();
    }


    @Override
    public void onClick(int type) {
        if (type == mConfirmDialog.OK){
            presenter.getData(ApiConfig.SEND_REGISTER_VERIFY, telephone_desc.getText().toString() + userName.getText().toString().trim());
        }
        mConfirmDialog.dismiss();
    }
}
