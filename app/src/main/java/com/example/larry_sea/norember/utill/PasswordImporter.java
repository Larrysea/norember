package com.example.larry_sea.norember.utill;

import android.content.Context;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.larry_sea.norember.manager.PasswordManager;
import com.example.larry_sea.norember.utill.encipher.AESKeyModel;
import com.example.larry_sea.norember.utill.encipher.AesUtil;
import com.example.larry_sea.norember.utill.encipher.MD5EncipherUtil;
import com.example.larry_sea.norember.utill.encipher.PasswordAnalyst;
import com.example.larry_sea.norember.utill.encipher.SHA1Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Larry-sea on 10/11/2016.
 * <p>
 * 密码导入类
 * <p>
 * 读取文件
 */

public class PasswordImporter {

    static String key = "dsfdsgdjsfhsakgduqb234khosue21";
    static String TAG = PasswordImporter.class.toString();
    String contentStr;    //内容数据
    String aesString;     //aesString
    String jsonString;    //jsonString
    JSONArray jsonArray;

    void readFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                InputStream inputStream = new FileInputStream(file);
                contentStr = StringUtil.inputStreamToString(inputStream);
            } catch (IOException e) {

            }
        }
    }


    /*
    * 解码
    *
    * */
    boolean decode() {
        aesString = PasswordAnalyst.decodeBase64ToAesString(contentStr);
        key = MD5EncipherUtil.md5(key);
        key = key.substring(0, 16);
        String[] jsonStringArray = PasswordAnalyst.decodeAesStringToJsonString(aesString, key.getBytes());
        AESKeyModel aesKeyModel = new AESKeyModel();
        try {
            AESKeyModel.encrypt("abcd".getBytes(), key.getBytes());

        } catch (java.lang.Exception e) {

        }
        if (jsonStringArray != null) {
            String sha1String = SHA1Util.encryptToSHA(jsonStringArray[0]);
            if (sha1String.equals(jsonStringArray[1])) {
                JSONObject jsonObject = PasswordAnalyst.stringToJsonObject(sha1String);
                jsonArray = PasswordAnalyst.getpasswordJsonObjectList(jsonObject);
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }


    /*
    * 直接获取jsonobject
    *
    * */
    JSONArray getJsonObject(String filePath) {
        readFile(filePath);
        decode();
        if (jsonArray != null) {
            return jsonArray;
        } else {
            return null;
        }

    }


    public void imPortFile(String filePath) {
        getJsonObject(filePath);
    }


    /*
    *
    * 解密加密的密码数据
    *
    * */
    public String decodeEncipherKey(Context context, String decipherPassword) {
        String key = PasswordManager.getPassword(context);
        AesUtil.getInstance();
        AESKeyModel aesKeyModel = new AESKeyModel();
        byte array[] = aesKeyModel.initSecretKey(key);
        try {
            return new String(AESKeyModel.decrypt(decipherPassword.getBytes(), array));
        } catch (java.lang.Exception exception) {
        }
        return null;
    }


}
