package com.zh.project_mvp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zh.data.TestBean;
import com.zh.project_mvp.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Context context;
    List<TestBean.DataInfo> dataInfoList = new ArrayList<>();

    public RecyclerViewAdapter(Context context) {
        this.context = context;
    }
    public void addData(List<TestBean.DataInfo> dataInfos){
        this.dataInfoList.addAll(dataInfos);
        notifyDataSetChanged();
    }
    public void clear (){
        this.dataInfoList.clear();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.style_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int i) {
        TestBean.DataInfo dataInfo = dataInfoList.get(i);
        viewHolder.title.setText(dataInfo.title);
        Glide.with(context).load(dataInfo.thumbnail).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return dataInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
        }
    }
}
