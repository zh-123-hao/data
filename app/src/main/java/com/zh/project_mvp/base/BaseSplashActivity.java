package com.zh.project_mvp.base;

import android.widget.ImageView;
import android.widget.TextView;

import com.zh.data.Device;
import com.zh.frame.FrameApplication;
import com.zh.frame.secret.SystemUtils;
import com.zh.project_mvp.R;
import com.zh.utils.utils.NetworkUtils;


/**
 * Created by 任小龙 on 2020/6/2.
 */
public abstract class BaseSplashActivity extends BaseMvpActivity {




    public void initDevice() {
        Device device = new Device();
        device.setScreenWidth(SystemUtils.getSize(this).x);
        device.setScreenHeight(SystemUtils.getSize(this).y);
        device.setDeviceName(SystemUtils.getDeviceName());
        device.setSystem(SystemUtils.getSystem(this));
        device.setVersion(SystemUtils.getVersion(this));
        device.setDeviceId(SystemUtils.getDeviceId(this));
        device.setLocalIp(NetworkUtils.getLocalIpAddress());
        FrameApplication.getFrameApplication().setDeviceInfo(device);
    }

}
