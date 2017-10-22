package com.example.larry_sea.norember.utill.commonutils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.util.DisplayMetrics;

import com.example.larry_sea.norember.R;

/**
 * Created by Larry-sea on 2016/10/15.
 * <p>
 * <p>
 * 常用语手机先关的工具类
 */

public class DeviceUtil {



    /*
    *
    * 获得屏幕尺寸  例如手机尺寸为4 英寸  获取的数据也为4左右
    *
    * */

    static public int getScreenSizeOfDevice2(Activity activity) {
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getRealSize(point);
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        double x = Math.pow(point.x / dm.xdpi, 2);
        double y = Math.pow(point.y / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        return (int) screenInches;
    }


    /*
    * 发送email的方法
    *
    * */
    static public void sendEmail(Context context, String address, String title, String content) {
        Intent data = new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse("mailto:" + address));
        data.putExtra(Intent.EXTRA_SUBJECT, R.string.very_nice_password_manager);
        data.putExtra(Intent.EXTRA_TEXT, content);
        context.startActivity(data);

    }


}
