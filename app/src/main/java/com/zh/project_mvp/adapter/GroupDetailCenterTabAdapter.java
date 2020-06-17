package com.zh.project_mvp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zh.data.GroupDetailEntity;
import com.zh.project_mvp.R;
import com.zh.project_mvp.interfaces.OnRecyclerItemClick;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 任小龙 on 2020/6/16.
 */
public class GroupDetailCenterTabAdapter extends RecyclerView.Adapter<GroupDetailCenterTabAdapter.ViewHolder> {

    private Context mContext;
    private List<GroupDetailEntity.Tag> mTabListData;

    public GroupDetailCenterTabAdapter(Context pContext, List<GroupDetailEntity.Tag> pTabListData) {
        mContext = pContext;
        mTabListData = pTabListData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.group_detail_tab_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //当前处于pop展开状态
        boolean tabSelected = mTabListData.get(position).isSelecting();
        holder.tagContent.setText(mTabListData.get(position).getOn() == 0 ?mTabListData.get(position).getTag_name() : mTabListData.get(position).getSelect_name());
        holder.tagContent.setBackground(ContextCompat.getDrawable(mContext,mTabListData.get(position).getOn()==1 && !tabSelected ? R.drawable.group_tab_bg_has_selected_content : R.drawable.group_tab_bg));
        holder.fallsView.setVisibility(tabSelected ? View.VISIBLE : View.INVISIBLE);
        holder.tagContent.setTextColor(ContextCompat.getColor(mContext,tabSelected ? R.color.red : mTabListData.get(position).getOn() == 1? R.color.red : R.color.black));
        holder.tagContent.setCompoundDrawablesWithIntrinsicBounds(0,0,tabSelected ? R.drawable.ic_menu_arrow_up_red : mTabListData.get(position).getOn() == 1?R.drawable.ic_menu_arrow_down_red:R.drawable.ic_menu_arrow_down_gray,0);
        holder.tagContent.setOnClickListener(v -> {
            if (mOnRecyclerItemClick != null)mOnRecyclerItemClick.onItemClick(position);
        });
    }

    private OnRecyclerItemClick mOnRecyclerItemClick;

    public void setOnRecyclerItemClick(OnRecyclerItemClick pOnRecyclerItemClick){
        mOnRecyclerItemClick = pOnRecyclerItemClick;
    }

    @Override
    public int getItemCount() {
        return mTabListData != null ? mTabListData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.falls_view)
        View fallsView;
        @BindView(R.id.tagContent)
        TextView tagContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
