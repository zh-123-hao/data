package com.zh.frame;

public interface ICommonModel<T> {
    /*
    * model层用于做耗时任务，用来处理加载和刷新
    * whichApi  区别接口的标识
    * param params 泛型可变参数
    * */

    void getData(ICommonPreseneter preseneter,int whichApi,T... params);
}
