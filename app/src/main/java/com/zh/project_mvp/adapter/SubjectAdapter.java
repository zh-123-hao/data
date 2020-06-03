package com.zh.project_mvp.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.zh.data.SpecialtyChooseEntity;
import com.zh.project_mvp.R;
import com.zh.utils.utils.newAdd.GlideUtil;
import java.util.List;
import io.reactivex.annotations.NonNull;


/**
 * Created by 任小龙 on 2020/6/2.
 */
public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {

    private List<SpecialtyChooseEntity> mList;
    private Context mContext;

    public SubjectAdapter(List<SpecialtyChooseEntity> pList, Context pContext) {
        mList = pList;
        mContext = pContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_child_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SpecialtyChooseEntity entity = mList.get(position);
        GlideUtil.loadImage(holder.leftRoundImage,entity.getIcon());
        holder.itemTitle.setText(entity.getBigspecialty());
        holder.itemRecyclerview.setLayoutManager(new GridLayoutManager(mContext,4));
        holder.itemRecyclerview.setAdapter(new SubjectChildAdapter(entity.getData(),mContext,this));
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView leftRoundImage;
        TextView itemTitle;
        RecyclerView itemRecyclerview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            leftRoundImage = itemView.findViewById(R.id.left_round_image);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemRecyclerview = itemView.findViewById(R.id.item_recyclerview);
        }
    }
}
