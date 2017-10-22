package com.example.larry_sea.norember.utill.encipher;

import android.util.Base64;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Larry-sea on 10/10/2016.
 * 密码解析器
 * <p>
 * <p>
 * 整个解析流程首先使用decodeBase64ToAesString()
 * <p>
 * 然后使用  decodeAesStringToJsonString()
 * <p>
 * 然后直接获取jsonstring 和jsonString sha1之后的数值然后进行数据合法性判断
 * <p>
 * 然后调用   stringToJSONobjct获取jsonobject然后在获取数据
 */


public class PasswordAnalyst {

    final static String TAG = "passwordanalyst";

    /*
    * 将加密的文件解析为asstr
    *
    *
    * */
    static public String decodeBase64ToAesString(String baseString) {
        return new String(Base64.decode(baseString, Base64.DEFAULT));
    }


    /*
    *
    *
    * 将aesString
    *
    *
    * 返回的list中包含两个值
    *
    * 第一个是jsonString
    *
    * 第二个是jsonsha1值进行数据检验 时使用
    *
    *
    * */
    static public String[] decodeAesStringToJsonString(String aesString, byte[] screatKey) {
        String decodedAesString = null;
        String[] result = new String[2];
        try {
            SecretKeySpec key_AES = new SecretKeySpec(screatKey, AESKeyModel.KEY_ALGORITHM);
            decodedAesString = new String(AESKeyModel.decrypt(aesString.getBytes(), key_AES));
        } catch (java.lang.Exception e) {
            Log.e(TAG, e.getMessage());
        }
        if (decodedAesString != null) {
            result = decodedAesString.split("@@@@");
        }
        return result;

    }


    /*
    * 将sha1加密的数据转换为jsonstring
    *
    * */
    /*public String sha1StringToJsonString(String sha1String) {

        *//*DigestSignatureSpi.SHA1 sha1Decoder = new DigestSignatureSpi.SHA1();
        sha1Decoder*//*

    }*/


    /*
    *
    * 将string类型数据转换位jsonobjct
    *
    * */
    static public JSONObject stringToJsonObject(String jsonString) {
        return JSON.parseObject(jsonString);
    }


    static public JSONArray getpasswordJsonObjectList(JSONObject jsonObject) {
        return JSONObject.parseArray(jsonObject.get("AllUserAccount").toString());
    }


}