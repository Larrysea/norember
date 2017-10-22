package com.example.larry_sea.norember.test;

import android.support.v4.app.Fragment;
import android.view.Menu;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.view.activity.BaseFragmentActivity;

/**
 * Created by Larry-sea on 2016/10/30.
 *
 *
 */

public class KeyStoreTestActivity extends BaseFragmentActivity {
    BasicAndroidKeyStoreFragment basicAndroidKeyStoreFragment;

    @Override
    protected Fragment createFragment() {
        basicAndroidKeyStoreFragment = new BasicAndroidKeyStoreFragment();
        return basicAndroidKeyStoreFragment;
    }


}
