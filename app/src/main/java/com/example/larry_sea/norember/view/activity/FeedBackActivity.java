package com.example.larry_sea.norember.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.utill.commonutils.ApplicationUtil;
import com.example.larry_sea.norember.view.customer.floatwindow.FloatWindowService;
import com.example.larry_sea.norember.view.fragment.FeedbackFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Larry-sea on 10/4/2016.
 * 反馈activity
 */

public class FeedBackActivity extends AppCompatActivity {
    FeedbackFragment feedbackFragment;
    @BindView(R.id.id_activity_base_toolbar)
    Toolbar idActivityBaseToolbar;
    boolean isLocked = false;                         //是否被锁屏

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        ButterKnife.bind(this);
        initView();


    }


    /*
    *
    * 初始化view
    *
    * */
    public void initView() {
        idActivityBaseToolbar.setNavigationIcon(R.mipmap.ic_navigation_small);
        idActivityBaseToolbar.setTitle(R.string.feedback);
        setSupportActionBar(idActivityBaseToolbar);
        feedbackFragment = new FeedbackFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.id_activity_base_container, feedbackFragment, "feedbackFragment").commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLocked)
            startActivityForResult(new Intent(FeedBackActivity.this, LockScreenActivity.class), 1);
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
        if (ApplicationUtil.isApplicationInBackground(FeedBackActivity.this)) {
            isLocked = true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}
