package com.zh.project_mvp.view;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zh.data.TestBean;
import com.zh.frame.ApiConfig;
import com.zh.frame.ICommonModel;
import com.zh.frame.LoadTypeConfig;
import com.zh.frame.utils.ParamHashMap;
import com.zh.project_mvp.R;
import com.zh.project_mvp.adapter.RecyclerViewAdapter;
import com.zh.project_mvp.base.BaseMvpActivity;
import com.zh.project_mvp.interfaces.DataListenter;
import com.zh.project_mvp.model.CommomModel;
import java.util.Map;

public class MainActivity extends BaseMvpActivity {

    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    int page_id = 0;
    private Map<String, Object> params;
    private static final String TAG = "MainActivity";
    private RecyclerViewAdapter adapter;


    @Override
    public void setUpData() {
        presenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.NORMAL, params, page_id);
    }

    @Override
    public void setUpView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mSmartRefreshLayout = findViewById(R.id.smartRefreshLayout);
        params = new ParamHashMap().add("c", "api").add("a", "getList");
        initRcyclerView(mRecyclerView, mSmartRefreshLayout, new DataListenter() {
            @Override
            public void dataTyoe(int mode) {
                if (mode == LoadTypeConfig.REFRESH) {
                    page_id = 0;
                    presenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.REFRESH, params, page_id);
                } else {
                    page_id++;
                    presenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.MORE, params, page_id);
                }
            }
        });
        adapter = new RecyclerViewAdapter(this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public ICommonModel setModel() {
        return new CommomModel();
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {

    }


}
