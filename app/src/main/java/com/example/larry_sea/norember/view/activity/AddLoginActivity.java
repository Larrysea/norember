package com.example.larry_sea.norember.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.utill.commonutils.ApplicationUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Larry-sea on 2016/9/11.
 */
public class AddLoginActivity extends AppCompatActivity {


    @BindView(R.id.id_activity_toolbar)
    Toolbar idActivityToolbar;
    @BindView(R.id.login_infor_web_site_rb)
    RadioButton loginInforWebSiteRb;
    @BindView(R.id.login_infor_wifi_rb)
    RadioButton loginInforWifiRb;
    @BindView(R.id.login_infor_mail_site_rb)
    RadioButton loginInforMailSiteRb;
    @BindView(R.id.login_infor_database_rb)
    RadioButton loginInforDatabaseRb;
    @BindView(R.id.login_infor_server_rb)
    RadioButton loginInforServerRb;
    @BindView(R.id.id_next_setup_btn)
    Button idNextSetupBtn;
    boolean isLocked = false;        //是否锁住

    @OnClick(R.id.id_next_setup_btn)
    void onClick(View view) {
        Intent intent = new Intent(getBaseContext(), AddWebSiteActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_login_infor);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        ButterKnife.bind(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (isLocked)
            startActivityForResult(new Intent(AddLoginActivity.this, LockScreenActivity.class), 1);

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
        if (ApplicationUtil.isApplicationInBackground(AddLoginActivity.this)) {
            isLocked = true;
        }
    }
}
