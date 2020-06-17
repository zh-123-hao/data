package com.zh.project_mvp.model;

import android.content.Context;

import com.zh.data.ThirdLoginData;
import com.zh.frame.ApiConfig;
import com.zh.frame.FrameApplication;
import com.zh.frame.ICommonModel;
import com.zh.frame.ICommonPreseneter;
import com.zh.frame.NetManger;
import com.zh.frame.constants.ConstantKey;
import com.zh.frame.secret.RsaUtil;
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
            case ApiConfig.REGISTER_PHONE :
                ParamHashMap add = new ParamHashMap().add("mobile", params[0]).add("code", params[1]);
                netManger.netWork(netManger.getService(context.getString(R.string.passport_api)).checkVerifyCode(add), preseneter, whichApi);
                break;
            case  ApiConfig.CHECK_PHONE_IS_USED :
                netManger.netWork(netManger.getService(context.getString(R.string.passport_api)).checkPhoneIsUsed(params[0]), preseneter, whichApi);
                break;
            case ApiConfig.SEND_REGISTER_VERIFY :
                netManger.netWork(netManger.getService(context.getString(R.string.passport_api)).sendRegisterVerify(params[0]), preseneter, whichApi);
                break;
            case ApiConfig.NET_CHECK_USERNAME :
                netManger.netWork(netManger.getService(context.getString(R.string.passport)).checkName(params[0]), preseneter, whichApi);
                break;
            case ApiConfig.COMPLETE_REGISTER_WITH_SUBJECT :
                ParamHashMap param = new ParamHashMap().add("username", params[0]).add("password", RsaUtil.encryptByPublic((String) params[1]))
                        .add("tel", params[2]).add("specialty_id", FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_id())
                        .add("province_id", 0).add("city_id", 0).add("sex", 0).add("from_reg_name", 0).add("from_reg", 0);
                netManger.netWork(netManger.getService(context.getString(R.string.passport_api)).registerCompleteWithSubject(param), preseneter, whichApi);
                break;
            case ApiConfig.ACCOUNT_LOGIN :
                ParamHashMap login = new ParamHashMap();
                login.add("ZLSessionID", "");
                login.add("seccode", "");
                login.add("loginName", params[0]);
                login.add("passwd", RsaUtil.encryptByPublic((String) params[1]));
                login.add("cookieday", "");
                login.add("ignoreMobile", "");
                netManger.netWork(netManger.getService(context.getString(R.string.passport_openapi)).loginByAccount(login), preseneter, whichApi);
                break;
            case ApiConfig.GET_WE_CHAT_TOKEN :
                ParamHashMap wxLogin = new ParamHashMap();
                wxLogin.add("appid", ConstantKey.WX_APP_ID);
                wxLogin.add("secret",  ConstantKey.WX_APP_SECRET);
                wxLogin.add("code",params[0]);
                wxLogin.add("grant_type", "authorization_code");
                netManger.netWork(netManger.getService(context.getString(R.string.wx_oauth)).getWechatToken(wxLogin), preseneter, ApiConfig.GET_WE_CHAT_TOKEN );
                break;
            case ApiConfig.POST_WE_CHAT_LOGIN_INFO:
                ThirdLoginData  data = (ThirdLoginData) params[0];
                ParamHashMap postLogin = new ParamHashMap();
                postLogin.add("openid",  data.openid);
                postLogin.add("type",  data.type);
                postLogin.add("url", "android");
                netManger.netWork(netManger.getService(context.getString(R.string.passport_api)).loginByWechat(postLogin), preseneter, ApiConfig.POST_WE_CHAT_LOGIN_INFO);
                break;
        }
    }
}
