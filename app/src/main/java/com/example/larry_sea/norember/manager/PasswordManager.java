package com.example.larry_sea.norember.manager;

import android.content.Context;
import android.util.Log;

import com.example.larry_sea.norember.utill.StringUtil;
import com.example.larry_sea.norember.utill.commonutils.MathUtil;
import com.example.larry_sea.norember.utill.encipher.MD5EncipherUtil;
import com.example.larry_sea.norember.utill.encipher.SHA256;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Larry-sea on 2016/9/1.
 * <p>
 * <p>
 * 密码管理类
 */
public class PasswordManager {

    final static String fileName = "norember.data";   //norember 用户的数据存储文件名
    final static String TAG = PasswordManager.class.toString();
    static String password;                           //用户密码
    static String salt;                               //用户密码加密用盐
    static String encipherPassword;                   //用户的加密之后的密码数据
    static String firstPassword;                      //第一次用户输入密码
    static String secondPassword;                     //第二次用户输入密码
    List<Object> passwordList;                        //保存所有密码的list
    Context context;
    int passwordLength;                               //密码的长度
    static String aesKey = "dsfdsgdjsfhsakgduqb234khosue21"; //aeskeky

    /*
    *
    * 调用直接将用户加密之后的密码  还有密码用盐 保存文件中
    *
    * */
    static public void savePassword(Context context, String mainPassword) {
        try {
            String userSalt = initSalt();
            String encipherPassword = encipherPassword(mainPassword, userSalt);
            saveToRom(context, encipherPassword, userSalt, mainPassword.length());
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, e.getMessage());
        } catch (java.lang.Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }

    /*
    * 初始化密码加密用盐
    *
    *
    * */
    static String initSalt() {
        long randTime = Calendar.getInstance().getTime().getTime();
        String randNumber = MathUtil.random(8);
        salt = SHA256.bin2hex(randTime + randNumber);
        return salt;
    }


    /*
    *
    * 验证用户密码是否正确
    *
    * */
    static public boolean isPasswordCorrect(Context context, String password) {
        try {
            if (encipherPassword(password, getUserInfo(context).get("userSalt")).equals(getUserInfo(context).get("userPassword")))
                return true;
            else
                return false;
        } catch (java.lang.Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return true;

    }

    /*
    * 获取用户加密之后的密码
    *
    * */
    static public String getPassword(Context context) {

        try {
            encipherPassword = getUserInfo(context).get("userPassword");
        } catch (java.lang.Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return encipherPassword;
    }


    /**
     * 把用户名和密码保存到手机ROM
     *
     * @param password 输入要保存的密码
     * @param userSalt 要保存的用户名
     * @return
     */
    static boolean saveToRom(Context context, String password, String userSalt, int passwordLength) throws Exception {
        //以私有的方式打开一个文件
        FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        String result = userSalt + ":" + password + ":" + passwordLength;
        fos.write(result.getBytes());
        fos.flush();
        fos.close();
        return true;
    }


    static public Map<String, String> getUserInfo(Context context) throws FileNotFoundException, Exception {
        /*File file = new File("data/data/lq.wangzhen.file/files/" + filename);
        FileInputStream fis = new FileInputStream(file);*/
        //以上的两句代码也可以通过以下的代码实现：
        FileInputStream fis = context.openFileInput(fileName);
        byte[] data = StringUtil.getBytes(fis);
        String result = new String(data);
        String results[] = result.split(":");
        Map<String, String> map = new HashMap<String, String>();
        map.put("userSalt", results[0]);
        map.put("userPassword", results[1]);
        map.put("passwordRealLength", results[2]);
        return map;
    }


    /*
    * 加密用户密码
    *
    * */
    static String encipherPassword(String password, String salt) {
        encipherPassword = SHA256.bin2hex(MD5EncipherUtil.md5(password) + salt);
        return encipherPassword;
    }


    /*
    *
    * 获取密码的真正长度
    *
    * */
    static public int getPasswordLength(Context context) {
        try {
            return Integer.parseInt(getUserInfo(context).get("passwordRealLength"));
        } catch (java.lang.Exception e) {

        }
        return -1;
    }

    static public void setSecondPassword(String password) {
        secondPassword = password;

    }

    public static String getFirstPassword() {
        return firstPassword;
    }

    static public void setFirstPassword(String password) {
        firstPassword = password;

    }

    static public void saveEncryptionKey(Context context, String key, String keyFile) {
        try {
            FileOutputStream fos = context.openFileOutput(keyFile, Context.MODE_PRIVATE);
            String result = key;
            fos.write(result.getBytes());
            fos.flush();
            fos.close();

        } catch (FileNotFoundException e) {

        } catch (java.lang.Exception e) {

        }
    }


    /*
    * 获取加密用盐
    * */
    static public String getSalt(Context context) throws FileNotFoundException, Exception {

        return getUserInfo(context).get("userSalt");

    }

    /*
    *
    * 获取加密密码
    * */
    static public String getEncipherPassword(Context context) throws Exception {

        return getUserInfo(context).get("userPassword");
    }

    static public String getAesKey() {
        return aesKey;
    }

}

