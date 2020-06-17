package com.zh.project_mvp.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zh.frame.LoadTypeConfig;
import com.zh.project_mvp.interfaces.DataListenter;

public class BaseActivity extends AppCompatActivity {
    public Application1907  mApplication;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (Application1907) getApplication();
    }

    public void initRcyclerView(RecyclerView pRecyclerView, SmartRefreshLayout pRefreshLayout, final DataListenter pDataListenter){
        if (pRecyclerView!=null){
            pRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        if (pRefreshLayout!=null){
            pRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    if (pDataListenter!=null){
                        pDataListenter.dataTye(LoadTypeConfig.REFRESH);
                    }
                }
            });
            pRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    if (pDataListenter!=null){
                        pDataListenter.dataTye(LoadTypeConfig.MORE);
                    }
                }
            });
        }
    }

    public void showLog(Object content){
        Log.e("睚眦", content.toString() );
    }

    public void showToast(Object content){
        Toast.makeText(getApplicationContext(), content.toString(), Toast.LENGTH_SHORT).show();
    }
}
