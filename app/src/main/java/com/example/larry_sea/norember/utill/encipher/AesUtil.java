package com.example.larry_sea.norember.utill.encipher;

import java.io.File;

import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Larry-sea on 10/7/2016.
 */

public class AesUtil {

    static AesUtil minstance;

    /*
    * 获取实例
    *
    * */
    public static AesUtil getInstance() {
        if (minstance != null) {
            return minstance;
        } else {
            return minstance = new AesUtil();
        }
    }

    /*
    *
    * aes加密文件
    * @param srcPath 源文件路径
    * @param encipherFilePath  加密文件存放路径
    *
    * */
    public boolean AESEncipherFile(String srcPath, String enCipherFilePath, String mainPassword) {
        File f = new File(srcPath);

        if (!f.exists() || f.isDirectory())
            return false;
        else {
            String prefix = f.getName().substring(0, f.getName().indexOf('.'));
            String suffix = f.getName().substring(f.getName().indexOf('.'));
//            enCipherFilePath = Environment.getExternalStorageDirectory() + File.separator + prefix + "AES_jiAMi" + suffix;

            AESKeyModel model_aes = new AESKeyModel();
            model_aes.setSrcFile(srcPath);
            model_aes.setDestionFile(enCipherFilePath);

            try {
                SecretKeySpec key_AES = new SecretKeySpec(model_aes.initSecretKey(mainPassword), AESKeyModel.KEY_ALGORITHM);
                model_aes.encryptionFile(key_AES);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    /*@param  decipherFilePath  解密文件的输出路径
    *
    * @param  key_AES           解密秘钥
    *
    * @param decipherFilePath   待解密文件路径
    *
    * @param destFilePath       解密之后文件的保存路径
    * */
    public boolean AesDecipher(String decipherFilePath, String outDecipherFilePath, String mainPassword) {

        File f = new File(decipherFilePath);
        if (!f.exists() || f.isDirectory())
            return false;
        else {
            String prefix = f.getName().substring(0, f.getName().indexOf('.'));
            String suffix = f.getName().substring(f.getName().indexOf('.'));

            AESKeyModel model_aes = new AESKeyModel();
            model_aes.setSrcFile(decipherFilePath);
            model_aes.setDestionFile(outDecipherFilePath);
            SecretKeySpec key_AES = new SecretKeySpec(model_aes.initSecretKey(mainPassword), AESKeyModel.KEY_ALGORITHM);
            try {
                model_aes.descryptionFile(key_AES);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }

}
