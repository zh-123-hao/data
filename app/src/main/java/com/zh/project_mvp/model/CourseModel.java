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
import com.zh.utils.utils.ext.ConstUtils;


public class CourseModel implements ICommonModel {
    NetManger mNetManger = NetManger.getInstance();
    private String subjectId = FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_id();
    private Context mContext = Application1907.get07ApplicationContext();
    @Override
    public void getData(ICommonPreseneter preseneter, int whichApi, Object[] params) {
        switch (whichApi){
            case ApiConfig.COURSE_CHILD:
                ParamHashMap add = new ParamHashMap();
                add.add("specialty_id", subjectId);
                add.add("page", params[2]);
                add.add("limit", Constants.LIMIT_NUM);
                add.add("course_type", params[1]);
                mNetManger.netWork(mNetManger.getService(mContext.getString(R.string.edu_url)).getCourseChildData(add), preseneter, whichApi, params[0]);
                break;
        }
    }
}
