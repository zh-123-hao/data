package com.zh.basepopo.design;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.zh.basepopo.R;

import java.util.List;


/**
 * Created by 任小龙 on 2020/3/3.
 */
public class SlideFromBottomRecycleAdapter extends RecyclerView.Adapter<SlideFromBottomRecycleAdapter.ViewHolder> {

    private List<String> mList;

    public SlideFromBottomRecycleAdapter(List<String> pList) {
        mList = pList;
    }

    @NonNull
    @Override
    public SlideFromBottomRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SlideFromBottomRecycleAdapter.ViewHolder holder, int position) {
        holder.content.setText(mList.get(position));
        holder.itemView.setOnClickListener(view -> {
            if (mClick != null) mClick.onclick(position);
        });
    }

    public interface PopClick {
        void onclick(int pos);
    }

    private PopClick mClick;

    public void setPopItemClick(PopClick pPopItemClick) {
        mClick = pPopItemClick;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content_slide);
        }
    }
}
