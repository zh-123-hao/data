package com.zh.project_mvp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import com.zh.project_mvp.R;
import com.zh.project_mvp.base.BaseMvpActivity;
import com.zh.project_mvp.model.CommomModel;

public class HomeActivity extends BaseMvpActivity<CommomModel> implements NavController.OnDestinationChangedListener {


    @Override
    public void setUpData() {

    }

    @Override
    public void setUpView() {
        NavController navController = Navigation.findNavController(this, R.id.project_fragment_control);
        navController.addOnDestinationChangedListener(this);
    }

    @Override
    public CommomModel setModel() {
        return new CommomModel();
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {

    }

    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
        String string = destination.getLabel().toString();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
