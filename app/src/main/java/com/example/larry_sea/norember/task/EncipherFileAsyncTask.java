package com.example.larry_sea.norember.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.entity.base_entity.SafeFileDB;
import com.example.larry_sea.norember.utill.commonutils.FileSizeUtil;
import com.example.larry_sea.norember.utill.commonutils.FileUtil;
import com.example.larry_sea.norember.utill.encipher.AesUtil;
import com.example.larry_sea.norember.utill.commonutils.DialogUtil;

import java.io.File;

import io.realm.Realm;

/**
 * Created by Larry-sea on 10/11/2016.
 * <p>
 * 加密的异步任务
 */

public class EncipherFileAsyncTask extends AsyncTask<String, Integer, Boolean> {

    Context context;
    ProgressDialog progressDialog;

    inforActivity inforActivity;
    Realm realm;



    /*
    * parms 0代表原始图片路径
    *
    * paras 1代表主密码
    *
    *
    * */

    public EncipherFileAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(final String... params) {
        final String destFilePath = FileUtil.getFilePath(params[0]) + "encipher" + FileUtil.getFileName(params[0]);
        boolean isSucceed = AesUtil.getInstance().AESEncipherFile(params[0], destFilePath, params[1]);
        if (isSucceed) {

            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    File file = new File(destFilePath);
                    SafeFileDB safeFiledb = realm.createObject(SafeFileDB.class);
                    safeFiledb.setFileName(FileUtil.getFileName(params[0]));
                    safeFiledb.setFileStoragePath(destFilePath);
                    safeFiledb.setFileSize(FileSizeUtil.convertFileSize(file.length()));
                }
            });
            realm.close();

        }
        return true;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = DialogUtil.getProgressDialog(context, R.string.encipher_file, R.string.encipher_file_ing);
        progressDialog.show();

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        progressDialog.dismiss();
        Toast.makeText(context, R.string.encipher_success, Toast.LENGTH_SHORT).show();
        this.inforActivity.updateActivity();

    }

    public void setInfroActivityInterface(inforActivity inforActivity) {
        this.inforActivity = inforActivity;
    }

    public interface inforActivity {
        void updateActivity();  //通知activity刷新界面  实现方在SafeFolderActivity

    }

}

