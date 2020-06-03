package com.zh.project_mvp.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
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
import com.zh.frame.FrameApplication;
import com.zh.frame.ICommonModel;
import com.zh.frame.constants.ConstantKey;
import com.zh.frame.secret.SystemUtils;
import com.zh.project_mvp.R;
import com.zh.project_mvp.base.BaseSplashActivity;
import com.zh.project_mvp.model.LauchModel;
import com.zh.utils.utils.newAdd.GlideUtil;
import com.zh.utils.utils.newAdd.SharedPrefrenceUtils;

import java.io.Serializable;

import io.reactivex.disposables.Disposable;



public class SplashActivity extends BaseSplashActivity{


    private SpecialtyChooseEntity.DataBean mSelectedInfo;
    private Disposable mSubscribe;

    private int preTime = 5;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1 :
                    if (preTime>0){
                        preTime--;
                        time_view.setText(preTime+""+"s");
                        handler.sendEmptyMessageDelayed(1, 1000);
                    }else if (preTime == 0){
                        jump();
                    }else {

                    }
                    break;
            }
            return false;
        }
    });
    private BaseInfo<MainAdEntity> info;
    private ImageView advert_image;
    private TextView time_view;


    @Override
    public int getLayoutID() {
        return R.layout.activity_main2;
    }
    @Override
    public void setUpData() {
        mSelectedInfo = SharedPrefrenceUtils.getObject(this, ConstantKey.SUBJECT_SELECT);
        String specialtyId = "";
        if (mSelectedInfo != null && !TextUtils.isEmpty(mSelectedInfo.getSpecialty_id())){
            mApplication.setSelectedInfo(mSelectedInfo);
            specialtyId = mSelectedInfo.getSpecialty_id();
        }
        Point realSize = SystemUtils.getRealSize(this);
        presenter.getData(ApiConfig.ADVERT, specialtyId,realSize.x,realSize.y);
        LoginInfo loginInfo = SharedPrefrenceUtils.getObject(this, ConstantKey.LOGIN_INFO);
        if (loginInfo != null && !TextUtils.isEmpty(loginInfo.getUid()))mApplication.setLoginInfo(loginInfo);
    }

    @Override
    public void setUpView() {
        advert_image = findViewById(R.id.advert_image);
        time_view = findViewById(R.id.time_view);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initDevice();
        time_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump();
                handler.removeMessages(1);
            }
        });
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
        time_view.setText(preTime+""+"s");
        handler.sendEmptyMessageDelayed(1, 1000);
    }

    private void jump() {
        if (mSubscribe !=null){
            mSubscribe.dispose();
        }

        startActivity(new Intent(this,mSelectedInfo != null && !TextUtils.isEmpty(mSelectedInfo.getSpecialty_id()) ? mApplication.isLogin() ? HomeActivity.class : SubjectActivity.class  : LoginActivity.class ));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscribe != null && !mSubscribe.isDisposed()) mSubscribe.dispose();
    }
}
