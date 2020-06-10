package com.zh.project_mvp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.zh.project_mvp.R;
import com.zh.project_mvp.adapter.MyFragmentAdapter;
import com.zh.project_mvp.base.BaseMvpFragment;
import com.zh.project_mvp.fragment.datafaragment.CourseChildFragment;
import com.zh.project_mvp.model.CourseModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseFragment extends BaseMvpFragment<CourseModel> {
    @BindView(R.id.slide_layout)
    SlidingTabLayout layoutSlide;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private Unbinder mUnbinder;
    public static final int TRAINTAB = 3;
    public static final int BESTTAB = 1;
    public static final int PUBLICTAB = 2;
    private List<String> titleList = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();
    private MyFragmentAdapter mAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_course;
    }

    @Override
    public CourseModel setModel() {
        return null;
    }

    @Override
    public void setUpView() {
        mAdapter = new MyFragmentAdapter(getChildFragmentManager(), mFragments, titleList);
        viewPager.setAdapter(mAdapter);
        layoutSlide.setViewPager(viewPager);
    }

    @Override
    public void setUpData() {
        Collections.addAll(titleList, "训练营", "精品课", "公开课");
        Collections.addAll(mFragments, CourseChildFragment.getInstance(TRAINTAB),CourseChildFragment.getInstance(BESTTAB),CourseChildFragment.getInstance(PUBLICTAB));
        mAdapter.notifyDataSetChanged();
        layoutSlide.notifyDataSetChanged();
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {

    }

}
