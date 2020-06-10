package com.zh.project_mvp.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.zh.project_mvp.R;
import com.zh.project_mvp.adapter.MyFragmentAdapter;
import com.zh.project_mvp.base.BaseFragment;
import com.zh.project_mvp.base.BaseMvpFragment;
import com.zh.project_mvp.fragment.datafaragment.DataGroupFragment;
import com.zh.project_mvp.fragment.datafaragment.RecentlyBestFragment;
import com.zh.project_mvp.model.DataModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataFragment extends BaseFragment {
    @BindView(R.id.tab_layout)
    SegmentTabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private List<Fragment> mFragments = new ArrayList<>();
    private MyFragmentAdapter mAdapter;
    private Unbinder mBind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        mBind = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Collections.addAll(mFragments, DataGroupFragment.newInstance(), RecentlyBestFragment.newInstance());
        String[] strings = {"资料小组", "最新精华"};
        mAdapter = new MyFragmentAdapter(getChildFragmentManager(), mFragments, Arrays.asList(strings));
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setTabData(strings);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBind != null) mBind.unbind();
    }
}
