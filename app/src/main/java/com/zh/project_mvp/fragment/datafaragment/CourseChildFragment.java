package com.zh.project_mvp.fragment.datafaragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zh.data.BaseInfo;
import com.zh.data.CourseListInfo;
import com.zh.data.SearchItemEntity;
import com.zh.frame.ApiConfig;
import com.zh.frame.LoadTypeConfig;
import com.zh.project_mvp.R;
import com.zh.project_mvp.adapter.CourseChildAdapter;
import com.zh.project_mvp.base.BaseMvpFragment;
import com.zh.project_mvp.interfaces.DataListenter;
import com.zh.project_mvp.model.CourseModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CourseChildFragment extends BaseMvpFragment<CourseModel> implements DataListenter{
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int mIndex;
    private int page = 1;
    private  List<SearchItemEntity> mList = new ArrayList<>();
    private CourseChildAdapter mAdapter;

    public static CourseChildFragment getInstance(int index) {
        CourseChildFragment courseChildFragment = new CourseChildFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("whichFragment", index);
        courseChildFragment.setArguments(bundle);
        return courseChildFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            mIndex = (int) getArguments().get("whichFragment");
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.refresh_list_layout;
    }

    @Override
    public CourseModel setModel() {
        return new CourseModel();
    }

    @Override
    public void setUpView() {
        initRcyclerView(recyclerView, refreshLayout,this);
        mAdapter = new CourseChildAdapter(mList,getActivity());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setUpData() {
        persenter.getData(ApiConfig.COURSE_CHILD, LoadTypeConfig.NORMAL,mIndex,page);
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
                BaseInfo<CourseListInfo>info = (BaseInfo<CourseListInfo>) pD[0];
                if (info.isSuccess()){
                    List<SearchItemEntity> lists = info.result.lists;
                    int load = (int) pD[1];
                    if (load == LoadTypeConfig.REFRESH){
                        refreshLayout.finishRefresh();
                        mList.clear();
                    }else if (load == LoadTypeConfig.MORE){
                        refreshLayout.finishLoadMore();
                    }
                    mList.addAll(lists);
                    mAdapter.notifyDataSetChanged();
                }
    }


    @Override
    public void dataTye(int mode) {
        if (mode == LoadTypeConfig.REFRESH)
            persenter.getData(ApiConfig.COURSE_CHILD, LoadTypeConfig.REFRESH, mIndex, 1);
        else {
            page++;
            persenter.getData(ApiConfig.COURSE_CHILD, LoadTypeConfig.MORE, mIndex, page);
        }
    }
}
