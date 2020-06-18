package com.zh.project_mvp.fragment.datafaragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zh.basepopo.basepopup.BasePopupWindow;
import com.zh.basepopo.design.GroupTabPopup;
import com.zh.data.BaseInfo;
import com.zh.data.GroupDetailEntity;
import com.zh.frame.ApiConfig;
import com.zh.frame.FrameApplication;
import com.zh.frame.LoadTypeConfig;
import com.zh.frame.LoadView;
import com.zh.frame.constants.ConstantKey;
import com.zh.frame.constants.Constants;
import com.zh.frame.utils.ParamHashMap;
import com.zh.project_mvp.R;
import com.zh.project_mvp.adapter.DataGroupDetailBottomAdapter;
import com.zh.project_mvp.adapter.GroupDetailCenterTabAdapter;
import com.zh.project_mvp.adapter.GroupDetailPopAdapter;
import com.zh.project_mvp.adapter.MyFragmentAdapter;
import com.zh.project_mvp.base.BaseMvpFragment;
import com.zh.project_mvp.interfaces.DataListenter;
import com.zh.project_mvp.model.DataModel;
import com.zh.project_mvp.view.HomeActivity;
import com.zh.project_mvp.view.LoginActivity;
import com.zh.utils.utils.newAdd.GlideUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zh.project_mvp.JumpConstant.DATAGROUPFRAGMENT_TO_LOGIN;
import static com.zh.project_mvp.JumpConstant.JUMP_KEY;

public class DataGroupDetailFragment extends BaseMvpFragment<DataModel> implements DataListenter {
    @BindView(R.id.image_back)
    ImageView imageBack;
    @BindView(R.id.iv_thumb)
    ImageView ivThumb;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_member_num)
    TextView tvMemberNum;
    @BindView(R.id.tv_post_num)
    TextView tvPostNum;
    @BindView(R.id.tv_focus)
    TextView tvFocus;
    @BindView(R.id.groupBack)
    ImageView groupBack;
    @BindView(R.id.groupTitle)
    TextView groupTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabRecycle)
    RecyclerView tabRecycle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.slide_layout)
    SlidingTabLayout slideLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private HomeActivity mActivity;
    private String mGid;
    private List<GroupDetailEntity.Tag> mTabListData = new ArrayList<>();
    private List<GroupDetailEntity.Thread> mBottomData = new ArrayList<>();
    private List<GroupDetailEntity.Tag.SelectsBean> mPopData = new ArrayList<>();
    private DataGroupDetailBottomAdapter mDataGroupDetailBottomAdapter;
    private GroupDetailCenterTabAdapter mGroupDetailCenterTabAdapter;
    private GroupDetailPopAdapter mPopAdapter;
    private GroupTabPopup mGroupTabPopup;
    private List<String> mContains = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();
    private MyFragmentAdapter mFragmentAdapter;
    private String mGroupName;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_data_group_detail;
    }

    @Override
    public DataModel setModel() {
        return new DataModel();
    }

    @Override
    public void setUpView() {
        mActivity = (HomeActivity) getActivity();
        if (getArguments()!=null){
            mGid = getArguments().getString(ConstantKey.GROU_TO_DETAIL_GID);
            mGroupName = getArguments().getString(ConstantKey.GROU_TO_DETAIL_NAME);
        }
        groupTitle.setVisibility(View.GONE);
        appBar.addOnOffsetChangedListener((pAppBarLayout, verticalOffset) -> {
            boolean space = Math.abs(verticalOffset) >= tvName.getBottom();
            groupTitle.setVisibility(space ? View.VISIBLE : View.GONE);
            toolbar.setBackgroundColor(space ? setColor(R.color.app_theme) : Color.TRANSPARENT);
        });
        initRcyclerView(recyclerView, refreshLayout, this);
        mDataGroupDetailBottomAdapter = new DataGroupDetailBottomAdapter(getContext(), mBottomData);
        recyclerView.setAdapter(mDataGroupDetailBottomAdapter);
        LinearLayoutManager ma = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        tabRecycle.setLayoutManager(ma);
        mGroupDetailCenterTabAdapter = new GroupDetailCenterTabAdapter(getContext(), mTabListData);
        tabRecycle.setAdapter(mGroupDetailCenterTabAdapter);
        mGroupDetailCenterTabAdapter.setOnRecyclerItemClick((pos, pObjects) -> {
            if (mTabListData.get(pos).getSelects() != null)
                clickCenterTab(pos);
            else showToast("该标签下没有选择条件");
        });
        mFragmentAdapter = new MyFragmentAdapter(getChildFragmentManager(), mFragments, titleList);
        viewPager.setAdapter(mFragmentAdapter);
        slideLayout.setViewPager(viewPager);
    }

    private int currentTabPos = -1;

    private void clickCenterTab(int pos) {
        currentTabPos = pos;
        GroupDetailEntity.Tag tag = mTabListData.get(pos);
        tag.setSelecting(!tag.isSelecting());
        if (mPopData.size() != 0) mPopData.clear();
        if (mContains.size() != 0) mContains.clear();
        mPopData.addAll(tag.getSelects());
        mContains.addAll(tag.getContainsName());
        mGroupDetailCenterTabAdapter.notifyItemChanged(pos);
        if (mGroupTabPopup == null) {
            mGroupTabPopup = new GroupTabPopup(getActivity());
            mGroupTabPopup.popRecycle.setLayoutManager(new GridLayoutManager(getContext(), 2));
            mPopAdapter = new GroupDetailPopAdapter(getContext(), mPopData, mContains);
            mGroupTabPopup.popRecycle.setAdapter(mPopAdapter);
        }
        mPopAdapter.notifyDataSetChanged();
        mGroupTabPopup.showPopupWindow(tabRecycle);
        mGroupTabPopup.setOnDismissListener(new BasePopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                tag.setSelecting(!tag.isSelecting());
                mGroupDetailCenterTabAdapter.notifyItemChanged(pos);
            }
        });
        mPopAdapter.setOnRecyclerItemClick((pos1, pObjects) -> {
            popTabClick(pos1);
        });
    }
    /**
     * popupWindow中的标签点击逻辑
     *
     * @param pos
     */
    private int currentPopPos = -1;

    private void popTabClick(int pos) {
        LoadView.getInstance(getActivity(),null).show();
        currentPopPos = pos;
        GroupDetailEntity.Tag.SelectsBean selectsBean = mPopData.get(pos);
        tags = tags.equals(selectsBean.getUrl())?"":selectsBean.getUrl();
        getFooterData(LoadTypeConfig.REFRESH);
    }

    @Override
    public void setUpData() {
        persenter.allowLoading(getActivity());
        persenter.getData(ApiConfig.GROUP_DETAIL, mGid);
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi) {
            case ApiConfig.GROUP_DETAIL:
                BaseInfo<GroupDetailEntity> baseInfo = (BaseInfo<GroupDetailEntity>) pD[0];
                if (baseInfo.isSuccess()) {
                    GroupDetailEntity groupDetailEntity = baseInfo.result;
                    setDetailData(groupDetailEntity);
                    if (groupDetailEntity.getHave_tag() == 1) {
                        mTabListData.addAll(groupDetailEntity.getTag_arr());
                        mGroupDetailCenterTabAdapter.notifyDataSetChanged();
                        mBottomData.addAll(baseInfo.result.getThread_list());
                        mDataGroupDetailBottomAdapter.notifyDataSetChanged();
                    } else {
                        Collections.addAll(titleList, "主题", "热帖", "精华");
                        ArrayList<GroupDetailEntity.Thread> threadList = groupDetailEntity.getThread_list();
                        Collections.addAll(mFragments, DataDetailChildFragment.newInstance(threadList), DataDetailChildFragment.newInstance(threadList), DataDetailChildFragment.newInstance(threadList));
                        slideLayout.notifyDataSetChanged();
                        mFragmentAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case ApiConfig.GROUP_DETAIL_FOOTER_DATA:
                LoadView.getInstance(getActivity(), null).dismiss();
                String s = pD[0].toString();
                try {
                    JSONObject bigJson = new JSONObject(s);
                    int errNo = bigJson.getInt("errNo");
                    if (errNo == 0) {
                        JSONObject result = bigJson.getJSONObject("result");
                        controlBottomList(result, pD);
                        controlClickPopTab();
                    }
                } catch (JSONException pE) {
                    pE.printStackTrace();
                }
                //当点击时创建，如果没有点击，直接刷新或加载更多，该对象为空
                if (mGroupTabPopup != null) mGroupTabPopup.dismiss();
                break;
            case ApiConfig.CLICK_TO_FOCUS:
                BaseInfo focusBase = (BaseInfo) pD[0];
                if (focusBase.isSuccess())tvFocus.setText("已关注");
                break;
            case ApiConfig.CLICK_CANCEL_FOCUS:
                BaseInfo cancelBase = (BaseInfo) pD[0];
                if (cancelBase.isSuccess())tvFocus.setText("关注");
                break;
        }
    }

    private void controlBottomList(JSONObject pResult, Object[] pD) {
        String thread_list = null;
        try {
            thread_list = pResult.getString("thread_list");
        } catch (JSONException pE) {
            pE.printStackTrace();
        }
        Gson gson = new Gson();
        List<GroupDetailEntity.Thread> list = gson.fromJson(thread_list, new TypeToken<List<GroupDetailEntity.Thread>>() {
        }.getType());
        int loadType = (int) pD[1];
        if (loadType == LoadTypeConfig.REFRESH) {
            refreshLayout.finishRefresh();
            mBottomData.clear();
        } else if (loadType == LoadTypeConfig.MORE) {
            refreshLayout.finishLoadMore();
            if (list.size() < Constants.LIMIT_NUM)
                refreshLayout.setNoMoreData(true);
        }
        mBottomData.addAll(list);
        mDataGroupDetailBottomAdapter.notifyDataSetChanged();
    }

    /**
     * 当点击pop的标签时，会通过网络请求筛选数据，网络回调中前端处理点选逻辑
     */
    private void controlClickPopTab() {
        //未进行tab点击时，刷新加载currentTabPos为-1，执行以下内容会角标越界
        if (currentTabPos != -1) {
            GroupDetailEntity.Tag tag = mTabListData.get(currentTabPos);
            //有一个默认选中的，第一次加载一成功加入选中集合，为防止以后重复添加，这里在点击时（已经完成第一次加载），将其设为0；
            if (tag.getOn() == 1) tag.setOn(0);

            List<String> containsName = tag.getContainsName();
            String name = tag.getSelects().get(currentPopPos).getName();
            if (containsName.contains(name)) {
                containsName.clear();
            } else {
                containsName.clear();
                containsName.add(name);
            }
            tag.setContainsName(containsName);
        }
    }

    private int page = 1;
    private String tags = "";

    private void getFooterData(int pNormal) {
        ParamHashMap add = new ParamHashMap().add("gid", mGid).add("page", page).add("limit", Constants.LIMIT_NUM);
        if (!TextUtils.isEmpty(tags)) add.add("tagall", tags);
        persenter.getData(ApiConfig.GROUP_DETAIL_FOOTER_DATA, pNormal, add);
    }

    private void setDetailData(GroupDetailEntity groupInfo) {
        GroupDetailEntity.Group groupInner = groupInfo.getGroupinfo();
        tvName.setText(groupInner.getGroup_name());
        groupTitle.setText(groupInner.getGroup_name());
        tvMemberNum.setText("成员 " + groupInner.getMember_num() + " 人");
        tvPostNum.setText("资料 " + groupInner.getThread_num() + " 篇");
        tvFocus.setText(groupInner.getIs_add() == 1 ? "已关注" : "关注");
        GlideUtil.loadCornerImage(ivThumb, groupInner.getLogo(), null, 10);
        GlideUtil.loadBlurredBackground(groupInner.getLogo(), imageBack);
    }

    @OnClick({R.id.tv_focus, R.id.groupBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_focus:
                boolean login = FrameApplication.getFrameApplication().isLogin();
                if (login){
                    if (tvFocus.getText().toString().equals("已关注")){//已经关注，取消关注
                        persenter.getData(ApiConfig.CLICK_CANCEL_FOCUS,mGid);//绿码
                    } else  {//没有关注，点击关注
                        persenter.getData(ApiConfig.CLICK_TO_FOCUS,mGid,mGroupName);
                    }
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class).putExtra(JUMP_KEY,DATAGROUPFRAGMENT_TO_LOGIN));
                }
                break;
            case R.id.groupBack:
                mActivity.mProjectController.navigate(R.id.dataGroup_back_to_home);
                break;
        }
    }


    @Override
    public void dataTye(int mode) {
        page = mode == LoadTypeConfig.REFRESH ? 1 : page + 1;
        getFooterData(mode);
    }
}
