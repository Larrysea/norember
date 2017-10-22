package com.example.larry_sea.norember.view.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.view.fragment.SettingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Larry-sea on 10/4/2016.
 * <p>
 * 设置activity
 */

public class SettingActivity extends AppCompatActivity {


    SettingFragment settingFragment;
    @BindView(R.id.id_activity_base_toolbar)
    Toolbar idActivityBaseToolbar;
    boolean isLocked = false;                         //是否被锁屏

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        initView();

    }

    /*
    * 初始化view
    *
    * */
    public void initView() {
        idActivityBaseToolbar.setNavigationIcon(R.mipmap.ic_navigation_small);
        idActivityBaseToolbar.setTitle(R.string.setting);
        setSupportActionBar(idActivityBaseToolbar);
        settingFragment = new SettingFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.id_activity_base_container, settingFragment, "settingFragmnt").commit();


    }


    @TargetApi(23)
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLocked)
            startActivityForResult(new Intent(SettingActivity.this, LockScreenActivity.class), 1);
    }


    @Override
    protected void onStop() {
        super.onStop();
       /* if (ApplicationUtil.isApplicationInBackground(SettingActivity.this)) {
            isLocked = true;
        }*/
    }


}
