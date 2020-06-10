package com.zh.project_mvp.view;

import android.content.Intent;
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
import butterknife.OnClick;

import static com.zh.project_mvp.JumpConstant.JUMP_KEY;
import static com.zh.project_mvp.JumpConstant.SPLASH_TO_SUB;
import static com.zh.project_mvp.JumpConstant.SUB_TO_LOGIN;

public class SubjectActivity extends BaseMvpActivity<LauchModel> {

    @BindView(R.id.back_image)
    ImageView back_image;
    @BindView(R.id.title_content)
    TextView title_content;
    @BindView(R.id.right_image)
    ImageView right_image;
    @BindView(R.id.more_content)
    TextView more_content;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<SpecialtyChooseEntity> mListData = new ArrayList<>();
    private SubjectAdapter adapter;
    private String mFrom;

    @Override
    public void setUpData() {
        List<SpecialtyChooseEntity> info = SharedPrefrenceUtils.getSerializableList(this, ConstantKey.SUBJECT_LIST);
        if (info != null) {
            mListData.addAll(info);
            adapter.notifyDataSetChanged();
        } else {
            presenter.getData(ApiConfig.SUBJECT);
        }
    }

    @Override
    public void setUpView() {
        mFrom = getIntent().getStringExtra(JUMP_KEY);
        more_content.setText("完成");
        title_content.setText(getString(R.string.select_subject));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SubjectAdapter(mListData, this);
        recyclerView.setAdapter(adapter);
        back_image = findViewById(R.id.back_image);
        more_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mApplication.getSelectedInfo() == null) {
                    showToast("请选择专业");
                    return;
                }
                if (mFrom.equals(SPLASH_TO_SUB)) {
                    if (mApplication.isLogin()) {
                        startActivity(new Intent(SubjectActivity.this, HomeActivity.class));
                    } else {
                        startActivity(new Intent(SubjectActivity.this, LoginActivity.class).putExtra(JUMP_KEY, SUB_TO_LOGIN));
                    }
                }
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

    @Override
    protected void onStop() {
        super.onStop();
        SharedPrefrenceUtils.putObject(this, ConstantKey.SUBJECT_SELECT, mApplication.getSelectedInfo());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.back_image)
    public void onImagebackClicked() {
        finish();
    }
}
