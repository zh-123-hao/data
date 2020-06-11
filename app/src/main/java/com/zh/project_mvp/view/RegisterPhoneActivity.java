package com.zh.project_mvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zh.data.BaseInfo;
import com.zh.frame.ApiConfig;
import com.zh.project_mvp.R;
import com.zh.project_mvp.base.BaseMvpActivity;
import com.zh.project_mvp.model.AccountModel;
import com.zh.project_mvp.utils.CheckUserNameAndPwd;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zh.project_mvp.JumpConstant.*;

public class RegisterPhoneActivity extends BaseMvpActivity<AccountModel> {

    @BindView(R.id.back_image)
    ImageView back_image;
    @BindView(R.id.title_content)
    TextView title_content;
    @BindView(R.id.clearAccount)
    ImageView clearAccount;
    @BindView(R.id.accountContent)
    EditText accountContent;
    @BindView(R.id.visibleImage)
    ImageView visibleImage;
    @BindView(R.id.accountSecret)
    EditText accountSecret;
    @BindView(R.id.next_page)
    TextView next_page;
    private String mPhoneNum;

    @Override
    public void setUpData() {
        title_content.setText("创建账号");
        mPhoneNum = getIntent().getStringExtra("phoneNum");
    }

    @Override
    public void setUpView() {
        accountContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clearAccount.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
                next_page.setSelected(s.length() > 0 && accountSecret.getText().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        accountSecret.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                visibleImage.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
                next_page.setSelected(s.length() > 0 && accountContent.getText().length() > 0);
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
        return R.layout.activity_register_phone;
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi){
            case ApiConfig.NET_CHECK_USERNAME :
                BaseInfo info = (BaseInfo) pD[0];
                if (info.isSuccess()){
                    presenter.getData(ApiConfig.COMPLETE_REGISTER_WITH_SUBJECT, accountContent.getText().toString(), accountSecret.getText().toString(), mPhoneNum);
                } else if (info.errNo == 114){
                    showToast("该用户名不可用");
                } else showToast(info.msg);
                break;
            case ApiConfig.COMPLETE_REGISTER_WITH_SUBJECT:
                BaseInfo info1  = (BaseInfo) pD[0];
                if (info1.errNo == 24 || info1.errNo == 114 || info1.isSuccess()){
                    showToast("注册成功");
                    startActivity(new Intent(this, LoginActivity.class).putExtra(JUMP_KEY, REGISTER_TO_LOGIN));
                    finish();
                }else  showToast(info1.msg);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back_image,R.id.clearAccount, R.id.visibleImage, R.id.next_page})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_image:
                //TODO: add click handling
                finish();
                break;
            case R.id.clearAccount:
                //TODO: add click handling
                accountContent.setText("");
                break;
            case R.id.visibleImage:
                //TODO: add click handling
                accountSecret.setTransformationMethod(visibleImage.isSelected() ? PasswordTransformationMethod.getInstance() : HideReturnsTransformationMethod.getInstance());
                visibleImage.setSelected(!visibleImage.isSelected());
                break;
            case R.id.next_page:
                //TODO: add click handling
                if (CheckUserNameAndPwd.verificationUsername(this, accountContent.getText().toString(), accountSecret.getText().toString()))
                    presenter.getData(ApiConfig.NET_CHECK_USERNAME, accountContent.getText().toString());
                break;
        }
    }
}
