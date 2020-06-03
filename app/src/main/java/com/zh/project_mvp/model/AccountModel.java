package com.zh.project_mvp.model;

import android.content.Context;

import com.zh.frame.ApiConfig;
import com.zh.frame.FrameApplication;
import com.zh.frame.ICommonModel;
import com.zh.frame.ICommonPreseneter;
import com.zh.frame.NetManger;
import com.zh.frame.utils.ParamHashMap;
import com.zh.project_mvp.R;
import com.zh.project_mvp.base.Application1907;

public class AccountModel implements ICommonModel {
    private NetManger netManger = NetManger.getInstance();
    private Context context = Application1907.get07ApplicationContext();
    @Override
    public void getData(ICommonPreseneter preseneter, int whichApi, Object[] params) {
        switch (whichApi){
            case ApiConfig.SEND_VERIFY :
                netManger.netWork(netManger.getService(context.getString(R.string.passport_openapi_user)).getLoginVerify((String) params[0]), preseneter, whichApi);
                break;
            case ApiConfig.VERIFY_LOGIN :
                netManger.netWork(netManger.getService(context.getString(R.string.passport_openapi_user)).loginByVerify(new ParamHashMap().add("mobile",params[0]).add("code",params[1])),preseneter,whichApi);
                break;
            case ApiConfig.GET_HEADER_INFO :
                String uid = FrameApplication.getFrameApplication().getLoginInfo().getUid();
                netManger.netWork(netManger.getService(context.getString(R.string.passport_api)).getHeaderInfo(new ParamHashMap().add("zuid",uid).add("uid",uid)),preseneter,whichApi);
                break;
        }
    }
}
