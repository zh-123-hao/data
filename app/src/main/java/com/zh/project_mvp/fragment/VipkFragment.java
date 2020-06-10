package com.zh.project_mvp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zh.project_mvp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VipkFragment extends Fragment {


    public VipkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vipk, container, false);
    }

}
