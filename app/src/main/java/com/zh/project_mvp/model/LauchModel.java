package com.zh.project_mvp.model;

import android.content.Context;
import android.text.TextUtils;

import com.zh.frame.ApiConfig;
import com.zh.frame.ICommonModel;
import com.zh.frame.ICommonPreseneter;
import com.zh.frame.NetManger;
import com.zh.frame.utils.ParamHashMap;
import com.zh.project_mvp.R;
import com.zh.project_mvp.base.Application1907;

public class LauchModel implements ICommonModel {
    private NetManger mManger = NetManger.getInstance();
    private Context mContext = Application1907.get07ApplicationContext();
    @Override
    public void getData(ICommonPreseneter preseneter, int whichApi, Object[] params) {

        switch (whichApi){
            case ApiConfig.ADVERT :
                ParamHashMap map =  new ParamHashMap().add("w", params[1]).add("h", params[2]).add("positions_id", "APP_QD_01").add("is_show", 0);
                if (!TextUtils.isEmpty((String)params[0]))map.add("specialty_id", params[0]);
                mManger.netWork(mManger.getService(mContext.getString(R.string.ad_openapi)).getAdvert(map),preseneter,whichApi);
                break;
            case ApiConfig.SUBJECT:
                mManger.netWork(mManger.getService(mContext.getString(R.string.edu_openapi)).getSubjectList(), preseneter, whichApi);
                break;
        }
    }
}
