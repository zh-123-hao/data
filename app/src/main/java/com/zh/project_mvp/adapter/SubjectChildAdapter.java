package com.zh.project_mvp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zh.data.SpecialtyChooseEntity;
import com.zh.frame.FrameApplication;
import com.zh.project_mvp.R;

import java.util.List;

/**
 * Created by 任小龙 on 2020/6/2.
 */
public class SubjectChildAdapter extends RecyclerView.Adapter<SubjectChildAdapter.ViewHolder> {
    private List<SpecialtyChooseEntity.DataBean> data;
    private Context mContext;
    private SubjectAdapter fatherAdapter;

    public SubjectChildAdapter(List<SpecialtyChooseEntity.DataBean> pData, Context pContext,SubjectAdapter fatherAdapter) {
        data = pData;
        mContext = pContext;
        this.fatherAdapter = fatherAdapter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.subject_child_adapter_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.label.setText(data.get(position).getSpecialty_name());
        if (FrameApplication.getFrameApplication().getSelectedInfo() != null && !TextUtils.isEmpty(FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_id()) && FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_id().equals(data.get(position).getSpecialty_id())) {
            holder.label.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            holder.label.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_subject_selected));
        } else {
            holder.label.setTextColor(ContextCompat.getColor(mContext, R.color.fontColor333));
            holder.label.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_radius_white_bg));
        }
        holder.label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameApplication.getFrameApplication().setSelectedInfo(data.get(position));
                fatherAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView label;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.subject_label);
        }
    }
}
