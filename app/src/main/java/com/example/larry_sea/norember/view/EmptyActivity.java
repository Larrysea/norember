package com.example.larry_sea.norember.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.larry_sea.norember.MainActivity;
import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.manager.PasswordManager;
import com.example.larry_sea.norember.task.DeleteDataAsyncTask;
import com.example.larry_sea.norember.utill.internet_util.InternetUtil;
import com.example.larry_sea.norember.view.activity.LockScreenActivity;
import com.example.larry_sea.norember.view.activity.WelcomeActivity;

/**
 * Created by Larry-sea on 10/14/2016.
 * <p>
 * <p>
 * 登录的空activity
 */

public class EmptyActivity extends AppCompatActivity implements InternetUtil.inforEmptyActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (PasswordManager.getPassword(this) == null) {
            this.finish();
            startActivity(new Intent(this, WelcomeActivity.class));
        } else {
            /*InternetUtil internetUtil = new InternetUtil();
            internetUtil.setInforEmptyInterface(this);
            internetUtil.isLocked(EmptyActivity.this, "larrysea");   //查询后台是否设备处于锁定状态*/
            startActivityForResult(new Intent(this, LockScreenActivity.class), 1);

        }
    }

    @Override
    public void inforEmptyActivity(boolean isLocked) {
        if (isLocked) {
            DeleteDataAsyncTask deleteDataAsyncTask = new DeleteDataAsyncTask(EmptyActivity.this);
            deleteDataAsyncTask.execute();
        } else {
//            startActivity(new Intent(this, LockScreenActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            startActivity(new Intent(EmptyActivity.this, MainActivity.class));
            this.finish();
        } else if (resultCode == -1) {
            Toast.makeText(EmptyActivity.this, R.string.password_not_correct, Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }


}
