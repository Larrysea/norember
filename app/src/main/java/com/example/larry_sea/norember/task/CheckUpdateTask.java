package com.example.larry_sea.norember.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.utill.commonutils.ApplicationUtil;
import com.example.larry_sea.norember.utill.commonutils.CommonUtil;
import com.example.larry_sea.norember.utill.commonutils.DialogUtil;
import com.example.larry_sea.norember.utill.internet_util.InternetUtil;

/**
 * Created by Larry-sea on 2016/10/28.
 * <p>
 * 检查应用的版本信息
 */

public class CheckUpdateTask extends AsyncTask<String, Integer, Boolean> implements InternetUtil.checkUpdateTaskImpl {


    ProgressDialog mProgresssdialog;   //dialog
    Context mContext;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgresssdialog = DialogUtil.getProgressDialog(mContext, R.string.check_update, R.string.check_update);
        mProgresssdialog.show();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        InternetUtil internetUtil = new InternetUtil();
        try {
            internetUtil.checkUpdate(mContext,ApplicationUtil.getVersionName(mContext));
            internetUtil.setCheckUpdateInterface(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }

    public CheckUpdateTask(Context context) {
        mContext = context;
    }


    /*
    *
    * 通知已经加载成功
    *
    * */

    @Override
    public void getTheResult(String fileUrl) {
        mProgresssdialog.dismiss();
        CommonUtil.startDownLoadingTask(mContext, fileUrl);

    }


}
