package com.example.larry_sea.norember.view.activity.storage_type_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.example.larry_sea.norember.broadcast.ScreenLockBroadCastReceiver;
import com.example.larry_sea.norember.utill.commonutils.ApplicationUtil;
import com.example.larry_sea.norember.view.activity.BaseFragmentActivity;
import com.example.larry_sea.norember.view.activity.LockScreenActivity;
import com.example.larry_sea.norember.view.fragment.storage_type_fragment.LoginInforFragment;

/**
 * Created by Larry-sea on 10/9/2016.
 * 显示登陆信息的activity
 */

public class LoginInforActivity extends BaseFragmentActivity {
    LoginInforFragment loginInforFragment;
    boolean isLocked = false;                         //是否被锁

    @Override
    protected Fragment createFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("key", getIntent().getStringExtra("key"));
        loginInforFragment = new LoginInforFragment();
        loginInforFragment.setArguments(bundle);
        return loginInforFragment;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && loginInforFragment.isEditAble()) {
            loginInforFragment.wetherSaveLoginInfor();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLocked)
            startActivityForResult(new Intent(LoginInforActivity.this, LockScreenActivity.class), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            isLocked = false;
        } else if (resultCode == -1) {
            isLocked = true;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (ApplicationUtil.isApplicationInBackground(LoginInforActivity.this) || ScreenLockBroadCastReceiver.isLocked) {
            isLocked = true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
