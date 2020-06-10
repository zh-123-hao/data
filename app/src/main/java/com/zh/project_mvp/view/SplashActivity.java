package com.zh.project_mvp.view;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.zh.data.BaseInfo;
import com.zh.data.LoginInfo;
import com.zh.data.MainAdEntity;
import com.zh.data.SpecialtyChooseEntity;
import com.zh.frame.ApiConfig;
import com.zh.frame.ICommonModel;
import com.zh.frame.constants.ConstantKey;
import com.zh.frame.secret.SystemUtils;
import com.zh.project_mvp.R;
import com.zh.project_mvp.base.BaseSplashActivity;
import com.zh.project_mvp.model.LauchModel;
import com.zh.utils.utils.newAdd.GlideUtil;
import com.zh.utils.utils.newAdd.SharedPrefrenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

import static com.zh.project_mvp.JumpConstant.*;


public class SplashActivity extends BaseSplashActivity {


    @BindView(R.id.bottom_image)
    ImageView Imagebottom;
    @BindView(R.id.advert_image)
    ImageView advert_image;
    @BindView(R.id.time_view)
    TextView time_view;
    private SpecialtyChooseEntity.DataBean mSelectedInfo;
    private Disposable mSubscribe;

    private int preTime = 5;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (preTime > 0) {
                        preTime--;
                        time_view.setText(preTime + "" + "s");
                        handler.sendEmptyMessageDelayed(1, 1000);
                    } else if (preTime == 0) {
                        jump();
                    } else {

                    }
                    break;
            }
            return false;
        }
    });
    private BaseInfo<MainAdEntity> info;


    @Override
    public int getLayoutID() {
        return R.layout.activity_main2;
    }

    @Override
    public void setUpData() {
        mSelectedInfo = SharedPrefrenceUtils.getObject(this, ConstantKey.SUBJECT_SELECT);
        String specialtyId = "";
        if (mSelectedInfo != null && !TextUtils.isEmpty(mSelectedInfo.getSpecialty_id())) {
            mApplication.setSelectedInfo(mSelectedInfo);
            specialtyId = mSelectedInfo.getSpecialty_id();
        }
        Point realSize = SystemUtils.getRealSize(this);
        presenter.getData(ApiConfig.ADVERT, specialtyId, realSize.x, realSize.y);
        LoginInfo loginInfo = SharedPrefrenceUtils.getObject(this, ConstantKey.LOGIN_INFO);
        if (loginInfo != null && !TextUtils.isEmpty(loginInfo.getUid()))
            mApplication.setLoginInfo(loginInfo);
    }

    @Override
    public void setUpView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initDevice();

    }

    @Override
    public ICommonModel setModel() {
        return new LauchModel();
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        info = (BaseInfo<MainAdEntity>) pD[0];
        GlideUtil.loadImage(advert_image, info.result.getInfo_url());
        time_view.setVisibility(View.VISIBLE);
        getTiem();
    }

    private void getTiem() {
        time_view.setText(preTime + "" + "s");
        handler.sendEmptyMessageDelayed(1, 1000);
    }

    private void jump() {
        if (mSubscribe != null)mSubscribe.dispose();
        if (mSelectedInfo != null && !TextUtils.isEmpty(mSelectedInfo.getSpecialty_id())){
            if (mApplication.isLogin()){
                startActivity(new Intent(this,HomeActivity.class));
            }else {
                startActivity(new Intent(this,LoginActivity.class).putExtra(JUMP_KEY,SPLASH_TO_LOGIN));
            }
        }else {
            startActivity(new Intent(this,SubjectActivity.class).putExtra(JUMP_KEY,SPLASH_TO_SUB));
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscribe != null && !mSubscribe.isDisposed()) mSubscribe.dispose();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bottom_image, R.id.advert_image, R.id.time_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bottom_image:
                //TODO: add click handling
                break;
            case R.id.advert_image:
                //TODO: add click handling

                break;
            case R.id.time_view:
                //TODO: add click handling
                jump();
                handler.removeMessages(1);
                break;
        }
    }
}
