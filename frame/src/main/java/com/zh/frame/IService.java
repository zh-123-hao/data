package com.zh.frame;

import com.zh.data.TestBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface IService {

    @GET(".")
    Observable<TestBean> getData(@QueryMap Map<String,Object> params, @Query("page_id") int page_id);
}
