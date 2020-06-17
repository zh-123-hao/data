package com.zh.project_mvp.fragment.datafaragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zh.data.BaseInfo;
import com.zh.data.DataGroupListEntity;
import com.zh.frame.ApiConfig;
import com.zh.frame.FrameApplication;
import com.zh.frame.LoadTypeConfig;
import com.zh.frame.constants.ConstantKey;
import com.zh.project_mvp.R;
import com.zh.project_mvp.adapter.DataGroupAdapter;
import com.zh.project_mvp.base.BaseMvpFragment;
import com.zh.project_mvp.interfaces.DataListenter;
import com.zh.project_mvp.interfaces.OnRecyclerItemClick;
import com.zh.project_mvp.model.DataModel;
import com.zh.project_mvp.view.HomeActivity;
import com.zh.project_mvp.view.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.zh.project_mvp.JumpConstant.DATAGROUPFRAGMENT_TO_LOGIN;
import static com.zh.project_mvp.JumpConstant.JUMP_KEY;
import static com.zh.project_mvp.adapter.DataGroupAdapter.FOCUS_TYPE;
import static com.zh.project_mvp.adapter.DataGroupAdapter.ITEM_TYPE;

public class DataGroupFragment extends BaseMvpFragment<DataModel> implements DataListenter,OnRecyclerItemClick {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int page = 1;
    private List<DataGroupListEntity> mList = new ArrayList<>();
    private DataGroupAdapter mAdapter;

    public static DataGroupFragment newInstance() {
        DataGroupFragment fragment = new DataGroupFragment();
        return fragment;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.refresh_list_layout;
    }

    @Override
    public DataModel setModel() {
        return new DataModel();
    }

    @Override
    public void setUpView() {
        initRcyclerView(recyclerView, refreshLayout, this);
        mAdapter = new DataGroupAdapter(mList, getActivity());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnRecyclerItemClick(this);
    }

    @Override
    public void setUpData() {
        persenter.getData(ApiConfig.DATA_GROUP, LoadTypeConfig.NORMAL,page);
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi){
            case ApiConfig.DATA_GROUP :
                BaseInfo<List<DataGroupListEntity>> info = (BaseInfo<List<DataGroupListEntity>>) pD[0];
                if (info.isSuccess()){
                    List<DataGroupListEntity> result = info.result;
                    if (page == 1){
                        mList.clear();
                        refreshLayout.finishRefresh();
                    }else {
                        refreshLayout.finishLoadMore();
                    }
                    mList.addAll(result);
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case ApiConfig.CLICK_CANCEL_FOCUS :
                BaseInfo base = (BaseInfo) pD[0];
                int clickPos = (int) pD[1];
                if (base.isSuccess()){
                    showToast("取消成功");
                    mList.get(clickPos).setIs_ftop(0);
                    mAdapter.notifyItemChanged(clickPos);
                }
                break;
            case ApiConfig.CLICK_TO_FOCUS:
                BaseInfo baseSuc = (BaseInfo) pD[0];
                int clickJoinPos = (int) pD[1];
                if (baseSuc.isSuccess()){
                    showToast("关注成功");
                    mList.get(clickJoinPos).setIs_ftop(1);
                    mAdapter.notifyItemChanged(clickJoinPos);
                }
                break;
        }
    }

    @Override
    public void onItemClick(int pos, Object[] pObjects) {
        if (pObjects!=null&&pObjects.length!=0){
            switch ((int)pObjects[0]){
                case ITEM_TYPE:
                    HomeActivity activity = (HomeActivity) getActivity();
                    Bundle bundle = new Bundle();
                    bundle.putString(ConstantKey.GROU_TO_DETAIL_GID,mList.get(pos).getGid());
                    activity.mProjectController.navigate(R.id.dataGroupDetailFragment,bundle);
                break;
                case FOCUS_TYPE :
                    boolean login = FrameApplication.getFrameApplication().isLogin();
                    if (login){
                        DataGroupListEntity entity = mList.get(pos);
                        if (entity.isFocus()){
                            persenter.getData(ApiConfig.CLICK_CANCEL_FOCUS, entity.getGid(),pos);
                        }else {
                            persenter.getData(ApiConfig.CLICK_TO_FOCUS, entity.getGid(),entity.getGroup_name(),pos);
                        }
                    }else {
                       startActivity(new Intent(getContext(), LoginActivity.class).putExtra(JUMP_KEY,DATAGROUPFRAGMENT_TO_LOGIN));
                    }
                break;
            }
        }
    }

    @Override
    public void dataTye(int mode) {
        if (mode == LoadTypeConfig.REFRESH) {
            persenter.getData(ApiConfig.DATA_GROUP, LoadTypeConfig.REFRESH, 1);
        } else {
            page++;
            persenter.getData(ApiConfig.DATA_GROUP, LoadTypeConfig.MORE, page);
        }
    }
}
