package com.example.larry_sea.norember.utill.commonutils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.ArrayMap;

/**
 * Created by Larry-sea on 2016/10/26.
 * <p>
 * sharedpreference util
 *
 *
 *
 */

public class SharedPreferenceUtil {


    /*
    * 存储数据
    *
    * */
    @SuppressLint("NewApi")
    public void writeSharedPreference(Context context, String fileName, ArrayMap<String, String> parm) {
        SharedPreferences.Editor editor = context.getSharedPreferences(fileName, Activity.MODE_PRIVATE).edit();
        for (int position = 0; position < parm.size(); position++) {
            editor.putString((String) parm.keyAt(position), (String) parm.valueAt(position));
        }
        editor.commit();
    }


    /*
    *
    * 获取值
    * @param  fileName
    * @param  key键
    *
    * */
    static public String getStringSharedValue(Context context, String fileName, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Activity.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }


    static public boolean getBooleanSharedValue(Context context, String fileName, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Activity.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }




    /*
    * 存放单一的键值
    *
    * @param fileName文件名
    * @param value,key
    *
    *
    * 以覆盖的方式存值
    *
    * */
    static public void putSharedValue(Context context, String fileName, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(fileName, Activity.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    /*
    * putShareValue的原始函数
    *
    * */
    static public void putSharedValue(Context context, String fileName, String key, boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(fileName, Activity.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }


}
