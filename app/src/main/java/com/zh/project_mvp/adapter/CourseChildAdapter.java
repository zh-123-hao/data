package com.zh.project_mvp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.zh.data.SearchItemEntity;
import com.zh.project_mvp.R;
import com.zh.utils.utils.newAdd.GlideUtil;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


public class CourseChildAdapter extends RecyclerView.Adapter<CourseChildAdapter.ViewHodler> {

    private List<SearchItemEntity> mList;
    private Context mContext;

    public CourseChildAdapter(List<SearchItemEntity> pList, Context pContext) {
        mList = pList;
        mContext = pContext;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHodler(LayoutInflater.from(mContext).inflate(R.layout.course_adapter_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        if (mList == null || mList.size() == 0) return;
        SearchItemEntity entity = mList.get(position);
        holder.tvTitle.setText(entity.getLesson_name());
        GlideUtil.loadImage(holder.ivPhoto, entity.getThumb());
        holder.tvLearnNum.setText(entity.getStudentnum() + "人学习");
        holder.tvLikeNum.setText("好评率" + entity.getRate());
        holder.tvPrice.setText("￥" + entity.getPrice());
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_photo)
        ImageView ivPhoto;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_learn_num)
        TextView tvLearnNum;
        @BindView(R.id.tv_like_num)
        TextView tvLikeNum;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
