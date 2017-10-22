package com.example.larry_sea.norember.test;

import android.util.Base64;

import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Erik Hellman
 */
public class ClientEncryptionSamples {
    static byte mSalt[];
    static byte mIv[];

    public static SecretKey generateKey(char[] password, byte[] salt)
            throws Exception {
        int iterations = 1000;
        int outputKeyLength = 256;
        SecretKeyFactory secretKeyFactory
                = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec keySpec = new PBEKeySpec(password, salt,
                iterations, outputKeyLength);
        byte[] keyBytes = secretKeyFactory.generateSecret(keySpec).getEncoded();
        return new SecretKeySpec(keyBytes, "AES");
    }

    public static String encryptClearText(char[] password, String plainText)
            throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        int saltLength = 8;
        byte[] salt = new byte[saltLength];
        secureRandom.nextBytes(salt);
        mSalt = salt;
//        SecretKey secretKey = generateKey(password, salt);
        byte passwordArray[] = new String(password).getBytes();
        SecretKey secretKey1 = new SecretKeySpec(passwordArray, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] initVector = new byte[cipher.getBlockSize()];
//        secureRandom.nextBytes(initVector);
        initVector = "0000000000123456".getBytes();
        mIv = initVector;
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initVector);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey1, ivParameterSpec);
        byte[] cipherData = cipher.doFinal(plainText.getBytes("UTF-8"));
//        byte[] cipherData = cipher.doFinal(plainText.getBytes());
//        return Base64.encodeToString(cipherData, Base64.NO_WRAP | Base64.NO_PADDING);
        return Base64.encodeToString(cipherData, Base64.DEFAULT);

    }

    public static String decryptData(char[] password, String encodedData)
            throws Exception {

        byte[] encodeByte = Base64.decode(encodedData, Base64.DEFAULT);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] initVector = new byte[cipher.getBlockSize()];
//        secureRandom.nextBytes(initVector);
        initVector = "0000000000123456".getBytes();
        IvParameterSpec ivParams = new IvParameterSpec(initVector);
//        SecretKey secretKey = generateKey(password, mSalt);
        byte passwordArray[] = new String(password).getBytes();
        SecretKey secretKey1 = new SecretKeySpec(passwordArray, "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey1, ivParams);
        return new String(cipher.doFinal(encodeByte), "UTF-8");
//        return new String(cipher.doFinal(encodeByte));
    }


}
