package com.example.larry_sea.norember.view.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.larry_sea.norember.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Larry-sea on 2016/9/11.
 * 打开设置自动登录activity
 */
public class OpenAutoLoginSetting extends AppCompatActivity {

    @BindView(R.id.id_activity_open_auto_login_btn)
    Button idActivityOpenAutoLoginBtn;
    boolean isLocked = false;                         //是否被锁屏
    final static int OPEN_AUTO_LOGIN = 0;             //打开openauto loginservices
    final static int OPEN_SYSTEM_ALERT_WINDOW = 1;      //打开允许授权requestcode
    @BindView(R.id.id_activity_open_auto_login_tv)
    TextView idActivityOpenAutoLoginTv;

    @OnClick(R.id.id_activity_open_auto_login_btn)
    void onClick(View view) {
        if (idActivityOpenAutoLoginBtn.getText().toString().equals(getResources().getString(R.string.authorize))) {
            askSystemAlertWindowPermission();
        } else if (idActivityOpenAutoLoginBtn.getText().toString().equals(getResources().getString(R.string.open_auto_login_services))) {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivityForResult(intent, OPEN_AUTO_LOGIN);
        } else if (idActivityOpenAutoLoginBtn.getText().toString().equals(getResources().getString(R.string.you_have_finished))) {
            OpenAutoLoginSetting.this.onBackPressed();
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_auto_login);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
      /*  if (isLocked)
            startActivityForResult(new Intent(OpenAutoLoginSetting.this, LockScreenActivity.class), 1);*/
    }


    @TargetApi(23)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_AUTO_LOGIN) {
            //当用户点击了 打开辅助服务之后结果
            if (Build.VERSION.SDK_INT > 23) {
                if (!Settings.canDrawOverlays(this)) {
                    Toast.makeText(OpenAutoLoginSetting.this, R.string.denied_you_will_not_use_complete_feature, Toast.LENGTH_SHORT).show();
                }
            }
            idActivityOpenAutoLoginBtn.setText(R.string.you_have_finished);
            //当用户点击了确认打开权限时 然后提示打开辅助服务
        } else if (requestCode == OPEN_SYSTEM_ALERT_WINDOW) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
                setOpenAutoLoginServices();
            } else if (!Settings.canDrawOverlays(this)) {
                idActivityOpenAutoLoginTv.setText(R.string.denied_you_will_not_use_complete_feature);
            }
        }
    }

    /*
    *
    * 申请系统应用覆盖窗口  对应权限system alert window
    * */
    @TargetApi(23)
    public void askSystemAlertWindowPermission() {
        if (!Settings.canDrawOverlays(OpenAutoLoginSetting.this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + OpenAutoLoginSetting.this.getPackageName()));
                startActivityForResult(intent, OPEN_SYSTEM_ALERT_WINDOW);
        } else if (Settings.canDrawOverlays(OpenAutoLoginSetting.this)) {
            setOpenAutoLoginServices();
        }

    }


    /*
    *
    * 初始化view
    * */
    public void initView() {
        if (Build.VERSION.SDK_INT < 23) {
            setOpenAutoLoginServices();
        } else if (Build.VERSION.SDK_INT >= 23) {
            setOpenAutoLoginServices();
           /* if (Settings.canDrawOverlays(this)) {
                setOpenAutoLoginServices();
            }*/
        }
    }


    /*
    * 设置ui为自动登录的ui
    *
    * */
    private void setOpenAutoLoginServices() {
        idActivityOpenAutoLoginTv.setText(R.string.open_auto_login_permission_infor);
        idActivityOpenAutoLoginBtn.setText(R.string.open_auto_login_services);
    }


}
