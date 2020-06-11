package com.zh.project_mvp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zh.data.BannerLiveInfo;
import com.zh.data.BaseInfo;
import com.zh.data.IndexCommondEntity;
import com.zh.frame.ApiConfig;
import com.zh.frame.LoadTypeConfig;
import com.zh.project_mvp.R;
import com.zh.project_mvp.adapter.MainHomeAdapter;
import com.zh.project_mvp.base.BaseMvpFragment;
import com.zh.project_mvp.interfaces.DataListenter;
import com.zh.project_mvp.model.MainPageModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainPageFragment extends BaseMvpFragment<MainPageModel> implements DataListenter {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int page = 1;
    private List<IndexCommondEntity> bottomList = new ArrayList<>();
    private List<String> bannerData = new ArrayList<>();
    private List<BannerLiveInfo.Live> liveData = new ArrayList<>();
    private MainHomeAdapter mAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_main_page;
    }

    @Override
    public MainPageModel setModel() {
        return new MainPageModel();
    }

    @Override
    public void setUpView() {
        initRcyclerView(recyclerView, refreshLayout, this);
        mAdapter = new MainHomeAdapter(bottomList, bannerData, liveData, getActivity());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setUpData() {
        persenter.getData(ApiConfig.MAIN_PAGE_LIST, LoadTypeConfig.NORMAL,page);
        persenter.getData(ApiConfig.BANNER_LIVE, LoadTypeConfig.NORMAL);
    }
    private boolean mainList = false, banLive = false;
    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi){
            case ApiConfig.MAIN_PAGE_LIST :
                BaseInfo<List<IndexCommondEntity>> info = (BaseInfo<List<IndexCommondEntity>>) pD[0];
                int load = (int) pD[1];
                if (info.isSuccess()){
                    if (load == LoadTypeConfig.MORE){
                        refreshLayout.finishLoadMore();
                    }else if (load == LoadTypeConfig.REFRESH){
                        bottomList.clear();
                        refreshLayout.finishRefresh();
                    }
                    bottomList.addAll(info.result);
                    mainList = true;
                    if (banLive){
                        mAdapter.notifyDataSetChanged();
                        mainList = false;
                    }
                }else {
                    Toast.makeText(getActivity(), "列表加载错误", Toast.LENGTH_SHORT).show();
                }
                break;
            case ApiConfig.BANNER_LIVE :
                JsonObject jsonObject = (JsonObject) pD[0];
                try {
                    JSONObject object = new JSONObject(jsonObject.toString());
                    if (object.getString("errNo").equals("0")){
                        if (page == 1){
                            bannerData.clear();liveData.clear();
                        }
                        String result = object.getString("result");
                        JSONObject resultObject = new JSONObject(result);
                        String live = resultObject.getString("live");
                        if (live.equals("true") || live.equals("false")){
                            resultObject.remove("live");
                        }
                        result = resultObject.toString();
                        Gson gson = new Gson();
                        BannerLiveInfo bannerInFo = gson.fromJson(result, BannerLiveInfo.class);
                        for (BannerLiveInfo.Carousel data: bannerInFo.Carousel) {
                            bannerData.add(data.thumb);
                        }
                        if (bannerInFo.live!=null)liveData.addAll(bannerInFo.live);
                        banLive = true;
                        if (mainList){
                            mAdapter.notifyDataSetChanged();
                            banLive = false;
                        }
                    }
                } catch (JSONException pE) {
                    pE.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void dataTyoe(int mode) {
        if (mode == LoadTypeConfig.REFRESH) {
            mainList = false;
            banLive = false;
            persenter.getData(ApiConfig.MAIN_PAGE_LIST, LoadTypeConfig.REFRESH, 1);
            persenter.getData(ApiConfig.BANNER_LIVE, LoadTypeConfig.REFRESH);
        } else {
            page++;
            persenter.getData(ApiConfig.MAIN_PAGE_LIST, LoadTypeConfig.MORE, page);
        }
    }
}
