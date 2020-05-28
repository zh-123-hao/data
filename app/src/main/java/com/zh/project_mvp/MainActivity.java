package com.zh.project_mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zh.data.TestBean;
import com.zh.frame.ApiConfig;
import com.zh.frame.CommomModel;
import com.zh.frame.CommomPresenter;
import com.zh.frame.ICommonModel;
import com.zh.frame.ICommonPreseneter;
import com.zh.frame.ICommonView;
import com.zh.frame.LoadTypeConfig;
import com.zh.frame.utils.ParamHashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements ICommonView {

    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    int page_id = 0;
    private ICommonModel model;
    private ICommonPreseneter preseneter;
    private Map<String, Object> params;
    private List<TestBean.DataInfo> testBeanList = new ArrayList<>();
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        model = new CommomModel();
        preseneter = new CommomPresenter(this,model);
        preseneter.getData(ApiConfig.TEST_GET, LoadTypeConfig.NORMAL,params,page_id);
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.smartRefreshLayout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        params = new ParamHashMap().add("c", "api").add("a", "getList");


        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page_id = 0;
                preseneter.getData(ApiConfig.TEST_GET, LoadTypeConfig.REFRESH,params,page_id);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page_id++;
                preseneter.getData(ApiConfig.TEST_GET, LoadTypeConfig.MORE,params,page_id);
            }
        });



    }

    @Override
    public void onSuccess(int whichApi, int loadType, Object[] pD) {
        switch (whichApi){
            case ApiConfig.TEST_GET :
                if (loadType == LoadTypeConfig.MORE){
                    mSmartRefreshLayout.finishLoadMore();
                }else if(loadType == LoadTypeConfig.REFRESH){
                    mSmartRefreshLayout.finishRefresh();
                    testBeanList.clear();
                }
                TestBean testBean = (TestBean) pD[0];
                List<TestBean.DataInfo> datas = testBean.datas;
                testBeanList.addAll(datas);
                Log.d(TAG, "onSuccess: "+testBeanList.size());
                break;
        }
    }

    @Override
    public void onFailed(int whichApi, Throwable pThrowable) {

    }
}
