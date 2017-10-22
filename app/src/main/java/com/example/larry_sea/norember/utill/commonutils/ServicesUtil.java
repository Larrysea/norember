package com.example.larry_sea.norember.utill.commonutils;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;

/**
 * Created by Larry-sea on 10/6/2016.
 * <p>
 * 服务相关的工具类
 */

public class ServicesUtil {


    /*
    * 判断服务是否正在运行
    *
    * parm传进来正在运行的服务名称
    *
    * */
    static public boolean isServicesRunning(Context context, String servicesName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo serviceInfo : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceInfo.service.getClassName().equals(servicesName)) {
                return true;
            }
        }
        return false;
    }


}
