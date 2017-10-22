package com.example.larry_sea.norember.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.larry_sea.norember.utill.encipher.ExportData;
import com.example.larry_sea.norember.utill.encipher.ImportData;

/**
 * Created by Larry-sea on 2016/11/3.
 */

public class testExportData extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* ExportData exportData = new ExportData();
        exportData.initEnviorment(this);
        exportData.saveDataToFile();*/


        /*try {
            String aesString = new String(AESKeyModel.encrypt(testString.getBytes(), key.getBytes()));
            String deAesString = new String(AESKeyModel.decrypt(aesString.getBytes(), key.getBytes()));
            Log.e("aesString", deAesString);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        /*String encipherString = null;
        try {
            String result = encipherString = ClientEncryptionSamples.encryptClearText(key.toCharArray(), testString);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
      /*  ImportData importData = new ImportData();
        String filePath = Environment.getExternalStorageDirectory().getPath() + "/backUpData.norember";
        importData.importDataToRealm(filePath);
        importData.recycle();*/


    }
}
