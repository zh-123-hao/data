package com.zh.project_mvp.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zh.data.BaseInfo;
import com.zh.data.SpecialtyChooseEntity;
import com.zh.frame.ApiConfig;
import com.zh.frame.constants.ConstantKey;
import com.zh.project_mvp.R;
import com.zh.project_mvp.adapter.SubjectAdapter;
import com.zh.project_mvp.base.BaseMvpActivity;
import com.zh.project_mvp.model.LauchModel;
import com.zh.utils.utils.newAdd.SharedPrefrenceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubjectActivity extends BaseMvpActivity<LauchModel> {

    private List<SpecialtyChooseEntity> mListData = new ArrayList<>();

    private TextView title_content;
    private RecyclerView recyclerView;
    private SubjectAdapter adapter;
    private ImageView back_image;
    private ImageView right_image;
    private TextView more_content;

    @Override
    public void setUpData() {
        if (SharedPrefrenceUtils.getSerializableList(this, ConstantKey.SUBJECT_LIST) != null) {
            mListData.addAll(SharedPrefrenceUtils.<SpecialtyChooseEntity>getSerializableList(this, ConstantKey.SUBJECT_LIST));
            adapter.notifyDataSetChanged();
        } else
            presenter.getData(ApiConfig.SUBJECT);
    }

    @Override
    public void setUpView() {
        title_content = findViewById(R.id.title_content);
        recyclerView = findViewById(R.id.recyclerView);

        title_content.setText(getString(R.string.select_subject));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SubjectAdapter(mListData, this);
        recyclerView.setAdapter(adapter);
        back_image = findViewById(R.id.back_image);

        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public LauchModel setModel() {
        return new LauchModel();
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_subject;
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi) {
            case ApiConfig.SUBJECT:
                BaseInfo<List<SpecialtyChooseEntity>> info = (BaseInfo<List<SpecialtyChooseEntity>>) pD[0];
                mListData.addAll(info.result);
                adapter.notifyDataSetChanged();
                SharedPrefrenceUtils.putSerializableList(this, ConstantKey.SUBJECT_LIST, mListData);
                break;
        }
    }

}
