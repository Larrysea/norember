package com.example.larry_sea.norember.view.activity;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.larry_sea.norember.view.fragment.TestFragment;


/**
 * Created by Larry-sea on 2016/9/2.
 */

public class TestFragmentActivity extends BaseFragmentActivity {
    TestFragment mtestFragment;

    @Override
    protected Fragment createFragment() {

        mtestFragment = new TestFragment();
        return mtestFragment;
    }


}
