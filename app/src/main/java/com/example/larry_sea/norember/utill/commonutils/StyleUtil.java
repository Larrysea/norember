package com.example.larry_sea.norember.utill.commonutils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.example.larry_sea.norember.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by Larry-sea on 2016/10/22.
 * 关于视图的style util
 */

public class StyleUtil {


    /*
    * 设置透明的状态栏
    *
    * */
    static public void setTrasparentStausbar(Window window) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }


    }


    @TargetApi(19)
    static public void initWindow(Activity activity, Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager.setStatusBarTintColor(activity.getResources().getColor(R.color.black));
            tintManager.setStatusBarTintEnabled(true);
        }
    }

}
