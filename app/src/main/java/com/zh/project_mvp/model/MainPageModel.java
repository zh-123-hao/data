package com.zh.project_mvp.model;

import android.content.Context;

import com.zh.frame.ApiConfig;
import com.zh.frame.FrameApplication;
import com.zh.frame.ICommonModel;
import com.zh.frame.ICommonPreseneter;
import com.zh.frame.NetManger;
import com.zh.frame.constants.Constants;
import com.zh.frame.utils.ParamHashMap;
import com.zh.project_mvp.R;
import com.zh.project_mvp.base.Application1907;

public class MainPageModel implements ICommonModel {
    NetManger mNetManger = NetManger.getInstance();
    private Context mContext = Application1907.get07ApplicationContext();
    private String subjectId = FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_id();
    @Override
    public void getData(ICommonPreseneter preseneter, int whichApi, Object[] params) {
        switch (whichApi){
            case ApiConfig.MAIN_PAGE_LIST :
                ParamHashMap paramHashMap = new ParamHashMap();
                paramHashMap.add("specialty_id", subjectId);
                paramHashMap.add("page", params[1]);
                paramHashMap.add("limit", Constants.LIMIT_NUM);
                mNetManger.netWork(mNetManger.getService(mContext.getString(R.string.edu_openapi)).getCommonList(paramHashMap), preseneter, whichApi, params[0]);
                break;
            case ApiConfig.BANNER_LIVE :
                ParamHashMap live = new ParamHashMap();
                live.add("pro", subjectId);
                live.add("more_live", 1);
                live.add("is_new", 1);
                live.add("new_banner", 1);
                mNetManger.netWork(mNetManger.getService(mContext.getString(R.string.edu_openapi)).getBannerLive(live), preseneter, whichApi, params[0]);
                break;
        }
    }
}
