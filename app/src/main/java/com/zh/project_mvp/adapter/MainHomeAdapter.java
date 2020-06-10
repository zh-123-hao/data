package com.zh.project_mvp.adapter;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.zh.data.BannerLiveInfo;
import com.zh.data.IndexCommondEntity;
import com.zh.project_mvp.R;
import com.zh.project_mvp.view.design.BannerLayout;
import com.zh.utils.utils.ext.StringUtils;
import com.zh.utils.utils.newAdd.GlideUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainHomeAdapter extends RecyclerView.Adapter<MainHomeAdapter.ViewHolder> {

    private List<IndexCommondEntity> bottomList;
    private List<String> bannerData;
    private List<BannerLiveInfo.Live> liveData;
    private Activity mContext;

    public MainHomeAdapter(List<IndexCommondEntity> pBottomList, List<String> pBannerData, List<BannerLiveInfo.Live> pLiveData, Activity pContext) {
        bottomList = pBottomList;
        bannerData = pBannerData;
        liveData = pLiveData;
        mContext = pContext;
    }

    private final int BANNER = 1, LABEL = 2, LIVE = 3, RIGHT_IMAGE = 4, BIG_IMAGE = 5;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @LayoutRes int layoutId;
        if (viewType == BANNER) {
            layoutId = R.layout.item_homepage_ad;
        } else if (viewType == LABEL) {
            layoutId = R.layout.item_homepage_shortcuts;
        } else if (viewType == LIVE) {//recleview，
            layoutId = R.layout.live_recycler_item;
        } else if (viewType == BIG_IMAGE) {//大图
            layoutId = R.layout.item_big_image;
        } else {
            layoutId = R.layout.item_homepage_post;
        }
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false), viewType);
    }

    @Override
    public int getItemViewType(int position) {
        int type = RIGHT_IMAGE;
        if (position == 0) type = BANNER;
        else if (position == 1) type = LABEL;
        else if (liveData != null && liveData.size() != 0 && position == 2) type = LIVE;
        else {
            int usePos = liveData != null && liveData.size() != 0 ? position - 3 : position - 2;
            if (bottomList != null && bottomList.size() != 0) {
                if (bottomList.get(usePos).type == 3) {
                    type = RIGHT_IMAGE;
                } else type = BIG_IMAGE;
            }
        }

        return type;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            holder.banner.attachActivity(mContext);
            if (bannerData.size() != 0) holder.banner.setViewUrls(bannerData);
        } else if (getItemViewType(position) == LIVE) {
            LinearLayoutManager manager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
            holder.recyclerView.setLayoutManager(manager);
            LiveAdapter adapter = new LiveAdapter(liveData, mContext);
            holder.recyclerView.setAdapter(adapter);
        } else if (getItemViewType(position) == LABEL) {

        } else {
            if (bottomList.size() == 0) return;
            IndexCommondEntity entity = bottomList.get(liveData == null || liveData.size() == 0 ? position - 2 : position - 3);
            if (getItemViewType(position) == BIG_IMAGE) {
                holder.tvTitle.setText(entity.title);
                GlideUtil.loadCornerImage(holder.image, entity.pic, null, 6f);
                holder.studyNum.setText(entity.getView_num());
                holder.goodPercent.setText(entity.favorite_num);
            } else {
                holder.title.setText(StringUtils.translation(entity.title));
                GlideUtil.loadCornerImage(holder.ivPhoto, entity.pic, null, 6f);
                holder.tvAuthorAndTime.setText(entity.date);
                holder.tvCommentNum.setText((TextUtils.isEmpty(entity.reply_num) ? 0 : entity.reply_num) + "人跟帖");
                holder.tvBrowseNum.setText(entity.view_num + "人浏览");
            }
        }
    }

    @Override
    public int getItemCount() {
        return liveData != null && liveData.size() != 0 ? bottomList.size() + 3 : bottomList.size() + 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        BannerLayout banner;

        RecyclerView recyclerView;

        TextView tvTitle;
        ImageView image;
        TextView studyNum;
        TextView goodPercent;

        TextView title;
        TextView tvBrowseNum;
        TextView tvCommentNum;
        ImageView ivPhoto;
        TextView tvAuthorAndTime;

        public ViewHolder(@NonNull View itemView, int type) {
            super(itemView);
            if (type == BANNER) {
                banner = itemView.findViewById(R.id.banner_main);
            } else if (type == LIVE) {
                recyclerView = itemView.findViewById(R.id.recyclerView);
            } else if (type == BIG_IMAGE) {
                tvTitle = itemView.findViewById(R.id.tv_title);
                image = itemView.findViewById(R.id.image);
                studyNum = itemView.findViewById(R.id.study_num);
                goodPercent = itemView.findViewById(R.id.good_percent);
            } else if (type == RIGHT_IMAGE) {
                title = itemView.findViewById(R.id.tv_title_left);
                tvBrowseNum = itemView.findViewById(R.id.tv_browse_num);
                tvCommentNum = itemView.findViewById(R.id.tv_comment_num);
                ivPhoto = itemView.findViewById(R.id.iv_photo);
                tvAuthorAndTime = itemView.findViewById(R.id.tv_author_and_time);
            }
        }
    }
}
