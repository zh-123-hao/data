package com.zh.project_mvp.model;


import com.zh.frame.ApiConfig;
import com.zh.frame.ICommonModel;
import com.zh.frame.ICommonPreseneter;
import com.zh.frame.NetManger;

import java.util.Map;


public class CommomModel implements ICommonModel {
    NetManger netManger = NetManger.getInstance();
    @Override
    public void getData(final ICommonPreseneter preseneter, final int whichApi, Object[] params) {
        switch (whichApi){
            case ApiConfig.TEST_GET :
                final int loaType = (int) params[0];
                Map param = (Map) params[1];
                int pageId = (int) params[2];
                netManger.netWork(netManger.getService().getData(param, pageId), preseneter, whichApi, loaType);
            break;
        }
    }
}
