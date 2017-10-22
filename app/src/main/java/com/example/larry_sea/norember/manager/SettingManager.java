package com.example.larry_sea.norember.manager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Larry-sea on 10/11/2016.
 */

public class SettingManager {

    Context context;
    SharedPreferences sharesPreference = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharesPreference.edit();

    public void saveSetting(String key, boolean isChecked) {
        editor.putBoolean(key, isChecked);
    }


}
