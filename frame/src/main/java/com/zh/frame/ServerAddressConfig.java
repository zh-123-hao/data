package com.zh.frame;

/**
 * Created by 任小龙 on 2020/5/29.
 */
public class ServerAddressConfig {
    public static final int API_TYPE = 3;//1,内测  2，外测  3，外正
    public static String BASE_URL = "";
    static {
        if (API_TYPE == 1){
            BASE_URL = "";
        }
        if (API_TYPE == 2){
            BASE_URL = "";
        }
        if (API_TYPE == 3){
            BASE_URL = "http://static.owspace.com/";
        }
    }
}
