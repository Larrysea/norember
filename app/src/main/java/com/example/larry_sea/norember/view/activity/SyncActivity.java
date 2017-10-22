package com.example.larry_sea.norember.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.utill.PasswordImporter;
import com.example.larry_sea.norember.utill.commonutils.ApplicationUtil;
import com.example.larry_sea.norember.utill.commonutils.FileUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Larry-sea on 10/10/2016.
 * <p>
 * 选择导入方式的activity
 */

public class SyncActivity extends AppCompatActivity {

    final static String QQFILERECEIVEPATH = Environment.getExternalStorageState();
    final static String WECHATRECEIVEPATH = Environment.getExternalStorageState();
    @BindView(R.id.id_activity_base_toolbar)
    Toolbar idActivityBaseToolbar;
    PasswordImporter passwordImporter;
    boolean isLocked = false;                         //是否被锁屏

    @OnClick(R.id.id_activity_sync_qq_realitive_layout)
    void onClik(View view) {
        if (passwordImporter != null) {
            passwordImporter.imPortFile(QQFILERECEIVEPATH);
        }
    }

    @OnClick(R.id.id_activity_sync_wechat_realitive_layout)
    void onClick(View view) {
        if (passwordImporter != null) {
            passwordImporter.imPortFile(WECHATRECEIVEPATH);
        }

    }

    @OnClick(R.id.id_activity_sync_file_chooser_realitive_layout)
    void fileChooserOnClick(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        ButterKnife.bind(this);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        idActivityBaseToolbar.setNavigationIcon(R.mipmap.ic_navigation_small);
        idActivityBaseToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        passwordImporter = new PasswordImporter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String imagePath;
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            imagePath = FileUtil.getFilePahtFromUri(SyncActivity.this, uri);  //得到uri，后面就是将uri转化成file的过程。
            if (imagePath != null) {
                if (FileUtil.getFileType(imagePath).equals("norember"))
                    passwordImporter.imPortFile(imagePath);
                //AesUtil.getInstance().AesDecipher(imagePath, FileUtil.getFilePath(imagePath) + "decipher.png", "123456");
            }
        } else if (resultCode == 1) {
            isLocked = false;
        } else if (resultCode == -1) {
            isLocked = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLocked)
            startActivityForResult(new Intent(SyncActivity.this, LockScreenActivity.class), 1);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (ApplicationUtil.isApplicationInBackground(SyncActivity.this)) {
            isLocked = true;
        }
    }


}
