package com.example.larry_sea.norember.utill.encipher;


import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * Created by Larry-sea on 10/7/2016.
 */


public class AESKeyModel {

    public static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    private String srcFile = "", destionFile = "";

    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     * @return 密钥
     */
    public static Key toKey(byte[] key) {
        //生成密钥

        return new SecretKeySpec(key, KEY_ALGORITHM);
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[]   加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, Key key) throws Exception {
        return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key  二进制密钥
     * @return byte[]   加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }

    /**
     * 加密
     *
     * @param data            待加密数据
     * @param key             二进制密钥
     * @param cipherAlgorithm 加密算法/工作模式/填充方式
     * @return byte[]   加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] key, String cipherAlgorithm) throws Exception {
        //还原密钥
        Key k = toKey(key);
        return encrypt(data, k, cipherAlgorithm);
    }

    /**
     * 加密
     *
     * @param data            待加密数据
     * @param key             密钥
     * @param cipherAlgorithm 加密算法/工作模式/填充方式
     * @return byte[]   加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, Key key, String cipherAlgorithm) throws Exception {
        //实例化
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        //使用密钥初始化，设置为加密模式
        IvParameterSpec iv = new IvParameterSpec("0000000000123456".getBytes());           //larrysea增加的了偏移量
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        //执行操作
        return cipher.doFinal(data);
    }

    /**
     * 解密
     *
     * @param data 待解密数据
     * @param key  二进制密钥
     * @return byte[]   解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }

    /**
     * 解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[]   解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, Key key) throws Exception {
        return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }

    /**
     * 解密
     *
     * @param data            待解密数据
     * @param key             二进制密钥
     * @param cipherAlgorithm 加密算法/工作模式/填充方式
     * @return byte[]         解密数据
     * @throws Exception
     */
    static byte[] decrypt(byte[] data, byte[] key, String cipherAlgorithm) throws Exception {
        //还原密钥
        Key k = toKey(key);
        return decrypt(data, k, cipherAlgorithm);
    }

    /**
     * 解密
     *
     * @param data            待解密数据
     * @param key             密钥
     * @param cipherAlgorithm 加密算法/工作模式/填充方式
     * @return byte[]         解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, Key key, String cipherAlgorithm) throws Exception {
        //实例化
//        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM, new BouncyCastleProvider());    //larrysea重新配置cipher
        //使用密钥初始化，设置为解密模式
        IvParameterSpec iv = new IvParameterSpec("0000000000123456".getBytes());           //larrysea增加的了偏移量
        cipher.init(Cipher.DECRYPT_MODE, key, iv);                                          //larrysea修改了加密方式
        //执行操作
        return cipher.doFinal(data);
    }

    /**
     * 初始化密钥
     *
     * @return byte[] 密钥
     * @throws Exception
     */
    public byte[] initSecretKey(String mainPassword) {
        //返回生成指定算法的秘密密钥的 KeyGenerator 对象
        /*KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new byte[0];
        }
        //初始化此密钥生成器，使其具有确定的密钥大小
        //AES 要求密钥长度为 128
        kg.init(128);
        //生成一个密钥
        SecretKey secretKey = kg.generateKey();
*/
        String tempPassword = null;
        tempPassword = MD5EncipherUtil.md5(mainPassword);
        return tempPassword.substring(0, 16).getBytes();


    }

    public void setDestionFile(String destionFile) {
        this.destionFile = destionFile;
    }

    public void setSrcFile(String srcFile) {
        this.srcFile = srcFile;
    }

    public void encryptionFile(Key sessionKey) throws Exception {
        int len = 0;
        byte[] buffer = new byte[5 * 1024];
        byte[] cipherbuffer = null;

        // 使用会话密钥对文件加密。
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM, new BouncyCastleProvider());
        IvParameterSpec iv = new IvParameterSpec("0000000000123456".getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, sessionKey, iv);
        File destFile = new File(destionFile);
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        FileInputStream fis = new FileInputStream(new File(srcFile));
        FileOutputStream fos = new FileOutputStream(destFile);
        // 读取原文，加密并写密文到输出文件。
        while ((len = fis.read(buffer)) != -1) {
            cipherbuffer = cipher.update(buffer, 0, len);
            fos.write(cipherbuffer);
            fos.flush();
        }
        cipherbuffer = cipher.doFinal();
        fos.write(cipherbuffer);
        fos.flush();
        if (fis != null)
            fis.close();

        File file = new File(srcFile);
        if (file.exists() && file.isFile()) {
            file.getAbsoluteFile().delete();
        }

        if (fos != null)
            fos.close();


    }

    public void descryptionFile(Key sessionKey) throws Exception {
        int len = 0;
        byte[] buffer = new byte[5 * 1024];
        byte[] plainbuffer = null;

        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM, new BouncyCastleProvider());
        IvParameterSpec iv = new IvParameterSpec("0000000000123456".getBytes());
        cipher.init(Cipher.DECRYPT_MODE, sessionKey, iv);
        File destFile = new File(destionFile);
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        FileInputStream fis = new FileInputStream(new File(srcFile));
        FileOutputStream fos = new FileOutputStream(destFile);
        while ((len = fis.read(buffer)) != -1) {
            plainbuffer = cipher.update(buffer, 0, len);
            fos.write(plainbuffer);
            fos.flush();
        }
        plainbuffer = cipher.doFinal();
        fos.write(plainbuffer);
        fos.flush();
        if (fis != null)
            fis.close();
        if (fos != null)
            fos.close();
    }


}