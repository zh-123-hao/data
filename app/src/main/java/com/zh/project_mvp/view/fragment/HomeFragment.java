package com.zh.project_mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import com.zh.project_mvp.R;
import com.zh.project_mvp.view.design.BottomTabView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment implements BottomTabView.OnBottomTabClickCallBack, NavController.OnDestinationChangedListener {
    private List<Integer> normalIcon = new ArrayList<>();//为选中的icon
    private List<Integer> selectedIcon= new ArrayList<>();// 选中的icon
    private List<String> tabContent= new ArrayList<>();//tab对应的内容
    private NavController mNavController;
    private final int MAIN_PAGE = 1, COURSE = 2, VIP = 3, DATA = 4, MINE = 5;
    private BottomTabView mBottomTabView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBottomTabView = getView().findViewById(R.id.bottom_tab);
        Collections.addAll(normalIcon, R.drawable.main_page_view,R.drawable.course_view,R.drawable.vip_view,R.drawable.data_view,R.drawable.mine_view);
        Collections.addAll(selectedIcon, R.drawable.main_selected,R.drawable.course_selected,R.drawable.vip_selected,R.drawable.data_selected,R.drawable.mine_selected);
        Collections.addAll(tabContent, "主页","课程","VIP","资料","我的");
        mBottomTabView.setResource(normalIcon, selectedIcon, tabContent);

        mNavController = Navigation.findNavController(getView().findViewById(R.id.home_fragment_container));
        mNavController.addOnDestinationChangedListener(this);
        mBottomTabView.setOnBottomTabClickCallBack(this);

    }



    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        normalIcon.clear();
        selectedIcon.clear();
        tabContent.clear();
    }

    @Override
    public void clickTab(int tab) {
        switch (tab){
            case MAIN_PAGE:
                mNavController.navigate(R.id.mainPageFragment);
                break;
            case COURSE:
                mNavController.navigate(R.id.courseFragment);
                break;
            case VIP:
                mNavController.navigate(R.id.vipFragment);
                break;
            case DATA:
                mNavController.navigate(R.id.dataFragment);
                break;
            case MINE:
                mNavController.navigate(R.id.mineFragment);
                break;
        }
    }
}
