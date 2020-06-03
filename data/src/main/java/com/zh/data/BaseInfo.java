package com.zh.data;

import java.io.Serializable;

public class BaseInfo<D> implements Serializable {
    private static final long serialVersionUID = 1699855389705285737L;
    //private static final long serialVersionUID = 7178751760258280801L;

    public int errNo;
    public int exeTime;
    public String msg;
    public D result;

    public boolean isSuccess() {
        return errNo == 0;
    }
}
