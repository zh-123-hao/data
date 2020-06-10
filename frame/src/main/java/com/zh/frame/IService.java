package com.zh.frame;

import com.google.gson.JsonObject;
import com.zh.data.BaseInfo;
import com.zh.data.CourseListInfo;
import com.zh.data.DataGroupListEntity;
import com.zh.data.IndexCommondEntity;
import com.zh.data.LoginInfo;
import com.zh.data.MainAdEntity;
import com.zh.data.PersonHeader;
import com.zh.data.SpecialtyChooseEntity;
import com.zh.data.TestBean;
import java.util.List;
import java.util.Map;
import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface IService {

    @GET(".")
    Observable<TestBean> getData(@QueryMap Map<String,Object> params, @Query("page_id") int page_id);


    @GET("ad/getAd")
    Observable<BaseInfo<MainAdEntity>> getAdvert(@QueryMap Map<String,Object> pMap);

    @GET("lesson/getAllspecialty")
    Observable<BaseInfo<List<SpecialtyChooseEntity>>> getSubjectList();

    @GET("loginByMobileCode")
    Observable<BaseInfo<String>> getLoginVerify(@Query("mobile") String mobile);

    @GET("loginByMobileCode")
    Observable<BaseInfo<LoginInfo>> loginByVerify(@QueryMap Map<String, Object> params);

    @POST("getUserHeaderForMobile")
    @FormUrlEncoded
    Observable<BaseInfo<PersonHeader>> getHeaderInfo(@FieldMap Map<String,Object> params);


    @GET("openapi/lesson/getLessonListForApi?")
    Observable<BaseInfo<CourseListInfo>> getCourseChildData( @QueryMap Map<String,Object> params);

    @GET("lesson/getIndexCommend")
    Observable<BaseInfo<List<IndexCommondEntity>>> getCommonList(@QueryMap Map<String,Object> params);

    @GET("lesson/getCarouselphoto")
    Observable<JsonObject> getBannerLive(@QueryMap Map<String,Object> params);

    @GET("group/getGroupList")
    Observable<BaseInfo<List<DataGroupListEntity>>> getGroupList(@QueryMap Map<String,Object> params);

    @POST("removeGroup")
    @FormUrlEncoded
    Observable<BaseInfo> removeFocus(@FieldMap Map<String,Object> params);

    @POST("joingroup")
    @FormUrlEncoded
    Observable<BaseInfo> focus(@FieldMap Map<String,Object> params);

}
