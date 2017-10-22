package com.example.larry_sea.norember.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.utill.commonutils.FileUtil;
import com.example.larry_sea.norember.utill.commonutils.OpenFileUtil;
import com.example.larry_sea.norember.utill.encipher.AesUtil;
import com.example.larry_sea.norember.utill.commonutils.DialogUtil;

/**
 * Created by Larry-sea on 10/11/2016.
 * <p>
 * 解密文件的异步任务
 */

public class DecipherFileAsyncTask extends AsyncTask<String, Integer, Boolean> {


    boolean isSucceed;
    ProgressDialog progressDialog;
    Context context;
    String destfilePath;


    public DecipherFileAsyncTask(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        destfilePath = FileUtil.getFilePath(params[0]) + FileUtil.getFileName(params[0]).substring(8);
        isSucceed = AesUtil.getInstance().AesDecipher(params[0], destfilePath, "5944");
        return true;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = DialogUtil.getProgressDialog(context, R.string.decipher_file, R.string.decipher_file_ing);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        progressDialog.dismiss();
        if (isSucceed) {
            Intent intent = OpenFileUtil.openFile(destfilePath);
            if (intent != null) {
                context.startActivity(intent);
            }
        } else {
            Toast.makeText(context, R.string.decipher_failure, Toast.LENGTH_SHORT).show();
        }

    }
}
