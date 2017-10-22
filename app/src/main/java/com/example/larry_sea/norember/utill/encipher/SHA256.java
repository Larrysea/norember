package com.example.larry_sea.norember.utill.encipher;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Larry-sea on 10/12/2016.
 */

public class SHA256 {


    private static byte[] getHash(String password) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        digest.reset();
        return digest.digest(password.getBytes());
    }


    /*
    *
    * 二进制数据转十六进制
    *
    * */
    public static String bin2hex(String strForEncrypt) {
        byte[] data = getHash(strForEncrypt);
        return String.format("%0" + (data.length * 2) + "X", new BigInteger(1, data));
    }

}
