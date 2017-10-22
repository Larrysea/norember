package com.example.larry_sea.norember.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.entity.base_entity.SafeFileDB;
import com.example.larry_sea.norember.task.EncipherFileAsyncTask;
import com.example.larry_sea.norember.utill.commonutils.ApplicationUtil;
import com.example.larry_sea.norember.utill.commonutils.FileUtil;
import com.example.larry_sea.norember.view.fragment.NoContentFragment;
import com.example.larry_sea.norember.view.fragment.SafeFolderFragment;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Larry-sea on 10/7/2016.
 * <p>
 * <p>
 * 安全文件夹的activity
 */

public class SafeFolderActivity extends AppCompatActivity implements EncipherFileAsyncTask.inforActivity {

    File mfile;                   //得到的文件
    Realm realm;
    @BindView(R.id.id_activity_common_list_toolbar)
    Toolbar idActivityCommonListToolbar;
    ProgressDialog progressDialog;
    boolean isLocked = false;                         //是否被锁屏

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_list);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        initView();
        // AesUtil.getInstance().AesDecipher();
    }

    /*
        *
        * 打开文件的回复结果处理
        *
        * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String imagePath;
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            imagePath = FileUtil.getFilePahtFromUri(SafeFolderActivity.this, uri);  //得到uri，后面就是将uri转化成file的过程。
            if (imagePath != null) {
                EncipherFileAsyncTask encipherAsyncTask = new EncipherFileAsyncTask(this);
                encipherAsyncTask.execute(imagePath, "5944");
                encipherAsyncTask.setInfroActivityInterface(this);
                //AesUtil.getInstance().AesDecipher(imagePath, FileUtil.getFilePath(imagePath) + "decipher.png", "123456");

            }


        } else if (resultCode == 1) {
            isLocked = false;
        } else if (resultCode == -1) {
            isLocked = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
        }
    }

    /*
    * 判断数据是否为空
    *
    * */
    public boolean judgeEmpty() {
        realm.beginTransaction();
        if (realm.where(SafeFileDB.class).findAll().size() == 0) {
            realm.commitTransaction();
            return true;
        } else {
            realm.commitTransaction();
            return false;
        }

    }

    public void initView() {
        idActivityCommonListToolbar.setTitle(R.string.safe_folder);
        idActivityCommonListToolbar.setNavigationIcon(R.mipmap.ic_navigation_small);
        idActivityCommonListToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(idActivityCommonListToolbar);
        initFragment();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.safe_folder_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.id_menu_safe_folder_add) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, 1);
        }
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateActivity() {
        initFragment();
    }

    public void initFragment() {
        if (!judgeEmpty()) {
            SafeFolderFragment safeFolderFragment = new SafeFolderFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.id_activity_common_list_container, safeFolderFragment, "safeFolderFragment").commit();
        } else {
            NoContentFragment noContentFragment = new NoContentFragment();
            Bundle bundle = new Bundle();
            bundle.putString("noContentInfor", getResources().getString(R.string.no_content_tip));
            noContentFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.id_activity_common_list_container, noContentFragment, "safeFolderFragment").commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLocked)
            startActivityForResult(new Intent(SafeFolderActivity.this, LockScreenActivity.class), 1);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (ApplicationUtil.isApplicationInBackground(SafeFolderActivity.this)) {
            isLocked = true;
        }
    }
}


