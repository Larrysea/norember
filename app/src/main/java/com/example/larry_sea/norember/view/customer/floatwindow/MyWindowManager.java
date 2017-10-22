package com.example.larry_sea.norember.view.customer.floatwindow;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.utill.commonutils.ServicesUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MyWindowManager {

    private static final int LOGGEDIN = 1;            //已登录
    private static final int NOTLOGGED = 2;           //未登录
    private static final int REGISTER = 3;            //注册类型
    /**
     * 小悬浮窗View的实例
     */
    public static FloatWindowSmallView smallWindow;
    /**
     * 大悬浮窗View的实例
     */
    public static FloatWindowBigView bigWindow;
    public static FloatWindowBigView.inforService minforServices;  //通知服务
    /**
     * 小悬浮窗View的参数
     */
    private static LayoutParams smallWindowParams;
    /**
     * 大悬浮窗View的参数
     */
    private static LayoutParams bigWindowParams;
    /**
     * 用于控制在屏幕上添加或移除悬浮窗
     */
    private static WindowManager mWindowManager;
    /**
     * 用于获取手机可用内存
     */
    private static ActivityManager mActivityManager;
    private static Context mcontext;                 //设备上下文
    private static boolean isPasswordCorrect = false;                 //密码是否正确

    /**
     * 创建一个小悬浮窗。初始位置为屏幕的右部中间位置。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void createSmallWindow(final Context context, FloatWindowBigView.inforService inforService) {
        WindowManager windowManager = getWindowManager(context);
        mcontext = context;
        minforServices = inforService;
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        if (smallWindow == null) {
            smallWindow = new FloatWindowSmallView(context);
            if (smallWindowParams == null) {
                smallWindowParams = new LayoutParams();
//                smallWindowParams.type = LayoutParams.TYPE_PHONE;
                smallWindowParams.type = LayoutParams.TYPE_TOAST;
                smallWindowParams.format = PixelFormat.RGBA_8888;
                smallWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | LayoutParams.FLAG_NOT_FOCUSABLE;
                smallWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                smallWindowParams.width = FloatWindowSmallView.viewWidth;
                smallWindowParams.height = FloatWindowSmallView.viewHeight;
                smallWindowParams.x = screenWidth;
                smallWindowParams.y = screenHeight / 2;
                smallWindowParams.windowAnimations = R.style.small_window_anim;
            }
            smallWindow.setParams(smallWindowParams);
            smallWindow.setBackgroundResource(android.R.color.transparent);
            windowManager.addView(smallWindow, smallWindowParams);
            //判断监测窗口的应用服务是否正在运行
            if (!ServicesUtil.isServicesRunning(mcontext, "FloatWindowService")) {
                mcontext.startService(new Intent(mcontext, FloatWindowService.class));
            }

        }
    }

    /**
     * 将小悬浮窗从屏幕上移除。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void removeSmallWindow(Context context) {
        if (smallWindow != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(smallWindow);
            smallWindow = null;
        }
    }

    /**
     * 创建一个大悬浮窗。位置为屏幕正中间。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void createBigWindow(Context context) {
        WindowManager windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        if (bigWindow == null&&context!=null) {
            bigWindow = new FloatWindowBigView(context);
            bigWindow.setInforService(minforServices);             //设置回调借口调用
            if (bigWindowParams == null) {
                bigWindowParams = new LayoutParams();
                bigWindowParams.x = screenWidth / 2 - FloatWindowBigView.viewWidth / 2;
                bigWindowParams.y = screenHeight / 2 - FloatWindowBigView.viewHeight / 2;
//                bigWindowParams.type = LayoutParams.TYPE_PHONE;
                bigWindowParams.type = LayoutParams.TYPE_TOAST;
                bigWindowParams.format = PixelFormat.RGBA_8888;
                bigWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                bigWindowParams.width = FloatWindowBigView.viewWidth;
                bigWindowParams.height = FloatWindowBigView.viewHeight;
            }
            windowManager.addView(bigWindow, bigWindowParams);
          //  windowManager.updateViewLayout(bigWindow,bigWindowParams);
        }
    }

    /**
     * 将大悬浮窗从屏幕上移除。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void removeBigWindow(Context context) {
        if (bigWindow != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeViewImmediate(bigWindow);
            bigWindow = null;
            isPasswordCorrect = false;
        }
    }



    /**
     * 是否有悬浮窗(包括小悬浮窗和大悬浮窗)显示在屏幕上。
     *
     * @return 有悬浮窗显示在桌面上返回true，没有的话返回false。
     */
    public static boolean isWindowShowing() {
        return smallWindow != null || bigWindow != null;
    }

    /**
     * 如果WindowManager还未创建，则创建一个新的WindowManager返回。否则返回当前已创建的WindowManager。
     *
     * @param context 必须为应用程序的Context.
     * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗。
     */
    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

    /**
     * 如果ActivityManager还未创建，则创建一个新的ActivityManager返回。否则返回当前已创建的ActivityManager。
     *
     * @param context 可传入应用程序上下文。
     * @return ActivityManager的实例，用于获取手机可用内存。
     */
    private static ActivityManager getActivityManager(Context context) {
        if (mActivityManager == null) {
            mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        }
        return mActivityManager;
    }

    /**
     * 计算已使用内存的百分比，并返回。
     *
     * @param context 可传入应用程序上下文。
     * @return 已使用内存的百分比，以字符串形式返回。
     */
    public static String getUsedPercentValue(Context context) {
        String dir = "/proc/meminfo";
        try {
            FileReader fr = new FileReader(dir);
            BufferedReader br = new BufferedReader(fr, 2048);
            String memoryLine = br.readLine();
            String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
            br.close();
            long totalMemorySize = Integer.parseInt(subMemoryLine.replaceAll("\\D+", ""));
            long availableSize = getAvailableMemory(context) / 1024;
            int percent = (int) ((totalMemorySize - availableSize) / (float) totalMemorySize * 100);
            return percent + "%";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "悬浮窗";
    }

    /**
     * 获取当前可用内存，返回数据以字节为单位。
     *
     * @param context 可传入应用程序上下文。
     * @return 当前可用内存。
     */
    private static long getAvailableMemory(Context context) {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        getActivityManager(context).getMemoryInfo(mi);
        return mi.availMem;
    }


}

