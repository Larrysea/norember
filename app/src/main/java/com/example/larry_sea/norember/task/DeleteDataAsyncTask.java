package com.example.larry_sea.norember.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.larry_sea.norember.utill.commonutils.ApplicationUtil;
import com.example.larry_sea.norember.utill.commonutils.RuntimeCmdManager;
import com.example.larry_sea.norember.view.activity.LockScreenActivity;
import com.example.larry_sea.norember.utill.commonutils.DialogUtil;

/**
 * Created by Larry-sea on 2016/10/16.
 * <p>
 * 删除本机数据的asynctask
 */

public class DeleteDataAsyncTask extends AsyncTask<String, Integer, Boolean> {


    ProgressDialog progressDialog;
    Context mContext;
    String TAG = DeleteDataAsyncTask.class.toString();

    public DeleteDataAsyncTask(Context context) {
        this.mContext = context;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String cmd = "pm clear " + ApplicationUtil.getAppPackageName(mContext);
        RuntimeCmdManager.clearAppUserData(cmd);
        try {
            Thread.sleep(3000);
        } catch (java.lang.InterruptedException e) {
            Log.e(TAG, e.getMessage());

        }
        return true;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = DialogUtil.getProgressDialog(mContext, "设备已锁定", "正在删除数据");
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (progressDialog != null)
            progressDialog.dismiss();
        Intent intent = new Intent(mContext, LockScreenActivity.class);
        mContext.startActivity(intent);

    }
}

