package com.example.larry_sea.norember.utill.commonutils;

import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Larry-sea on 2016/10/15.
 * <p>
 * 有关系统工具类的util
 */

public class ApplicationUtil {
    /*
   *
   * 获取应用的安装包名
   *
   * */
    public static String getAppPackageName(Context context) {
        try {
            String pkName = context.getPackageName();
            String versionName = context.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            int versionCode = context.getPackageManager()
                    .getPackageInfo(pkName, 0).versionCode;
            return pkName + "   " + versionName + "  " + versionCode;
        } catch (Exception e) {
        }
        return null;
    }

    /*
    * 启动评价activity
    *
    * */
    static public void startCommentActivity(Context context) {

        try {
            Uri uri = Uri.parse("market://details?id=" + getAppPackageName(context));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "Couldn't launch the market !", Toast.LENGTH_SHORT).show();
        }

    }


    /*
    *
    * 应用是否处于前台
    *
    * */
    public static boolean isApplicationInBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }




    //获取应用版本信息
    static public  String getVersionName(Context context) throws Exception {
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
    }

}
