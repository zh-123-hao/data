package com.zh.project_mvp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zh.project_mvp.R;
import com.zh.utils.utils.ext.ToastUtils;
import com.zh.utils.utils.newAdd.RegexUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginView extends RelativeLayout{
    @BindView(R.id.account_login)
    TextView accountLogin;
    @BindView(R.id.verify_login)
    TextView verifyLogin;
    @BindView(R.id.account_point)
    View accountPoint;
    @BindView(R.id.verify_point)
    View verifyPoint;
    @BindView(R.id.account_name)
    EditText accountName;
    @BindView(R.id.account_secrete)
    EditText accountSecrete;
    @BindView(R.id.account_module)
    LinearLayout accountModule;
    @BindView(R.id.area_code)
    TextView areaCode;
    @BindView(R.id.verify_name)
    EditText verifyName;
    @BindView(R.id.verify_code)
    EditText verifyCode;
    @BindView(R.id.get_verify_code)
    public TextView getVerifyCode;
    @BindView(R.id.login_press)
    TextView loginPress;
    @BindView(R.id.verify_area)
    ConstraintLayout verifyView;
    private Context mContext;
    private final boolean aBoolean;
    public final int ACCOUNT_TYPE = 1, VERIFY_TYPE = 2;
    public int mCurrentLoginType = ACCOUNT_TYPE;

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.loagin_view, this);
        ButterKnife.bind(this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoginView, 0, 0);
        aBoolean = typedArray.getBoolean(R.styleable.LoginView_isMoreType, true);
        initView();
        if (!aBoolean) {
            findViewById(R.id.more_type_group).setVisibility(GONE);
        }
    }

    private void initView() {
        loginPress.setEnabled(false);
        accountSecrete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>5 && !TextUtils.isEmpty(accountName.getText().toString().trim())){
                    loginPress.setEnabled(true);
                }else loginPress.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        verifyCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>3 && RegexUtil.isPhone(verifyName.getText().toString().trim())){
                    loginPress.setEnabled(true);
                }else {loginPress.setEnabled(false);}
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.account_login, R.id.verify_login, R.id.get_verify_code, R.id.login_press})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.account_login :
                accountLogin.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                accountPoint.setVisibility(VISIBLE);
                verifyLogin.setTextColor(ContextCompat.getColor(mContext, R.color.dark_gray));
                verifyPoint.setVisibility(INVISIBLE);
                accountModule.setVisibility(VISIBLE);
                verifyView.setVisibility(INVISIBLE);
                mCurrentLoginType = ACCOUNT_TYPE;
                break;
            case R.id.verify_login :
                accountLogin.setTextColor(ContextCompat.getColor(mContext, R.color.dark_gray));
                accountPoint.setVisibility(INVISIBLE);
                verifyLogin.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                verifyPoint.setVisibility(VISIBLE);
                accountModule.setVisibility(INVISIBLE);
                verifyView.setVisibility(VISIBLE);
                verifyPoint.setBackgroundColor(ContextCompat.getColor(mContext,R.color.red));
                mCurrentLoginType = VERIFY_TYPE;
                break;
            case R.id.get_verify_code :
                if (TextUtils.isEmpty(verifyName.getText().toString())) {
                    ToastUtils.show(mContext, "用户名为空");
                    return;
                }
                if (!RegexUtil.isPhone(verifyName.getText().toString().trim())) {
                    ToastUtils.show(mContext, "手机号格式错误");
                    return;
                }
                if (mLoginViewCallBack != null)
                    mLoginViewCallBack.sendVerifyCode(areaCode.getText().toString()+verifyName.getText().toString().trim());
                break;
            case R.id.login_press :
                String userName = "",passWord = "";
                if (mCurrentLoginType == ACCOUNT_TYPE){
                    userName = accountName.getText().toString().trim();
                    passWord = accountSecrete.getText().toString().trim();
                } else {
                    userName = verifyName.getText().toString().trim();
                    passWord = verifyCode.getText().toString().trim();
                }
                if (mLoginViewCallBack != null) mLoginViewCallBack.loginPress(mCurrentLoginType,userName,passWord);
                break;
        }
    }

    private LoginViewCallBack mLoginViewCallBack;

    public void setLoginViewCallBack(LoginViewCallBack pLoginViewCallBack) {
        mLoginViewCallBack = pLoginViewCallBack;
    }

    public interface LoginViewCallBack {
        void sendVerifyCode(String phoneNum);

        void loginPress(int type, String userName, String pwd);
    }
}
