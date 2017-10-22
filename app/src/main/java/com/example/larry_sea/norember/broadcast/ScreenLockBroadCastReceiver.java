package com.example.larry_sea.norember.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.example.larry_sea.norember.utill.commonutils.ApplicationUtil;

/**
 * Created by Larry-sea on 2016/10/23.
 * <p>
 * <p>
 * 接受屏幕是是否锁屏的receiver
 */

public class ScreenLockBroadCastReceiver extends BroadcastReceiver {

    private String action = null;
    Context mContext;
    public static boolean isLocked = false;
    IntentFilter filter;

    @Override
    public void onReceive(Context context, Intent intent) {
        action = intent.getAction();
        if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            if (!ApplicationUtil.isApplicationInBackground(mContext)) {
                isLocked = true;
            }
            // 锁屏
        }
    }

    public ScreenLockBroadCastReceiver(Context context) {
        mContext = context;
    }


    static public void delock() {
        isLocked = false;
    }

}
