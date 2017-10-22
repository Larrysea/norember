package com.example.larry_sea.norember.application;

import android.app.Application;
import android.os.Build;

import com.example.larry_sea.norember.test.SecurityConstants;
import com.example.larry_sea.norember.utill.encipher.KeyStoreProviderUtil;
import com.example.larry_sea.norember.view.customer.ToastUtil;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by Larry-sea on 10/11/2016.
 * <p>
 * 全局对象
 */

public class NoremberApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        ToastUtil.init(this);
    }


    /*
    * 当程序终止时
    * */
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public void initEncryptionKey() {
        KeyStoreProviderUtil keyStoreProviderUtil = new KeyStoreProviderUtil();
        byte publicKey[] = new byte[64];
        try {
            //todo 下面这个方法在android 24以上出现错误出现错误的是产生的秘钥位数不够
            keyStoreProviderUtil.setAlias(SecurityConstants.REALM_ALIAS);
            new SecureRandom().nextBytes(publicKey);
            if (Build.VERSION.SDK_INT > 24) {
                publicKey[62] = 'n';
                publicKey[63] = 'o';
            }
            System.arraycopy(keyStoreProviderUtil.getPublicKey(this), 0, publicKey, 0, 63);
            System.arraycopy(publicKey, 0, publicKey, 0, 63);

        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException | UnrecoverableEntryException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().encryptionKey(publicKey).build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

}

