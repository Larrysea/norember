package com.example.larry_sea.norember.view.customer.floatwindow;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.example.larry_sea.norember.utill.commonutils.ApplicationUtil;
import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FloatWindowService extends Service {


    final static String TAG = "float window service";
    /**
     * 用于在线程中创建或移除悬浮窗。
     */
    private Handler handler = new Handler();

    /**
     * 定时器，定时进行检测当前应该创建还是移除悬浮窗。
     */
    private Timer timer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 开启定时器，每隔0.5秒刷新一次
        if (timer == null) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new RefreshTask(), 0, 500);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Service被终止的同时也停止定时器继续运行
        timer.cancel();
        timer = null;
    }


    /**
     * 获得属于桌面的应用的应用包名称
     *
     * @return 返回包含所有包名的字符串列表
     */
    private List<String> getHomes() {
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = this.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo ri : resolveInfo) {
            names.add(ri.activityInfo.packageName);
        }
        return names;
    }

    class RefreshTask extends TimerTask {

        @Override
        public void run() {

            // 当前界面是桌面，且有悬浮窗显示，则移除悬浮窗。
            if (isHome()) {
                removeWindow();
                //当应用程序在显示中并且有window显示则让widnow消失
            } else if (!ApplicationUtil.isApplicationInBackground(getBaseContext()) && MyWindowManager.isWindowShowing()) {
                removeWindow();
                //当判断服务界面不是登陆界面或者注册界面时隐藏window
            }
            //当前界面是桌面且有悬浮窗显示则移除悬浮窗

        }


    }

    private void removeWindow() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                MyWindowManager.removeSmallWindow(getApplicationContext());
                MyWindowManager.removeBigWindow(getApplicationContext());
            }
        });
    }

    /*
    *
    * 判断当前界面是否是桌面
    *
    * */
    public boolean isHome() {
        String top = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) { //For versions less than lollipop
            ActivityManager am = ((ActivityManager) getSystemService(ACTIVITY_SERVICE));
            top = am.getRunningTasks(5).get(0).topActivity.getPackageName();
            Log.v(TAG, "top app = " + top);
        } else { //For versions Lollipop and above
            List<ActivityManager.RunningAppProcessInfo> processes = AndroidProcesses.getRunningAppProcessInfo(getApplicationContext());
            top = processes.get(0).processName;
        }
        return getHomes().contains(top);
    }


}
