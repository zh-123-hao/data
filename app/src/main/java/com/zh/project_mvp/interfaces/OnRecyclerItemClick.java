package com.zh.project_mvp.interfaces;


public interface OnRecyclerItemClick<T> {
    void onItemClick(int pos, T... pTS);
}
