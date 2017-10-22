package com.example.larry_sea.norember.view.customer;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Larry-sea on 2016/10/22.
 * <p>
 * 显示toast 的util
 */

public class ToastUtil {
    static Context mcontext;

    static public void show(int stringId) {
        Toast.makeText(mcontext, mcontext.getResources().getString(stringId), Toast.LENGTH_SHORT).show();
    }

    static public void init(Context context) {
        mcontext = context;
    }

}
