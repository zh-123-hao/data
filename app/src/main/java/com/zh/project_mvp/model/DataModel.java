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

import java.util.Map;

public class DataModel implements ICommonModel {

    @Override
    public void getData(ICommonPreseneter preseneter, int whichApi, Object[] params) {
        NetManger manger = NetManger.getInstance();
        Context mContext = Application1907.get07ApplicationContext();
        switch (whichApi){
            case ApiConfig.DATA_GROUP :
                ParamHashMap add = new ParamHashMap().add("type", 1).add("fid", FrameApplication.getFrameApplication().getSelectedInfo().getFid()).add("page", params[1]);
                manger.netWork(manger.getService(mContext.getString(R.string.bbs_openapi)).getGroupList(add),preseneter,whichApi,params[0]);
                break;
            case ApiConfig.CLICK_CANCEL_FOCUS :
                ParamHashMap add1 = new ParamHashMap().add("group_id", params[0]).add("type", 1).add("screctKey", FrameApplication.getFrameApplicationContext().getString(R.string.secrectKey_posting));
                if (params.length>1){
                    manger.netWork(manger.getService(mContext.getString(R.string.bbs_api)).removeFocus(add1), preseneter, whichApi, params[1]);
                }else {
                    manger.netWork(manger.getService(mContext.getString(R.string.bbs_api)).removeFocus(add1), preseneter, whichApi);
                }
                break;
            case ApiConfig.CLICK_TO_FOCUS :
                ParamHashMap add2 = new ParamHashMap().add("gid", params[0]).add("group_name", params[1]).add("screctKey", FrameApplication.getFrameApplicationContext().getString(R.string.secrectKey_posting));
                if (params.length>2){
                    manger.netWork(manger.getService(mContext.getString(R.string.bbs_api)).focus(add2), preseneter, whichApi, params[2]);
                }else {
                    manger.netWork(manger.getService(mContext.getString(R.string.bbs_api)).focus(add2), preseneter, whichApi);
                }

                break;
            case ApiConfig.GROUP_DETAIL :
                manger.netWork(manger.getService(mContext.getString(R.string.bbs_openapi)).getGroupDetail(params[0]), preseneter, whichApi);
                break;
            case ApiConfig.GROUP_DETAIL_FOOTER_DATA :
                manger.netWork(manger.getService(mContext.getString(R.string.bbs_openapi)).getGroupDetailFooterData((Map<String, Object>)params[1]), preseneter, whichApi, params[0]);
                break;
        }
    }
}
