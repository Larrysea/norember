package com.example.larry_sea.norember.utill;

import com.example.larry_sea.norember.entity.account_entity.BankCard;
import com.example.larry_sea.norember.entity.account_entity.BasePassword;
import com.example.larry_sea.norember.entity.account_entity.CreditCard;
import com.example.larry_sea.norember.entity.account_entity.IdentityCard;
import com.example.larry_sea.norember.entity.account_entity.LoginInfor;
import com.example.larry_sea.norember.entity.account_entity.MailInfor;
import com.example.larry_sea.norember.entity.account_entity.SafeNote;
import com.example.larry_sea.norember.entity.account_entity.Wifiinfor;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Larry-sea on 10/12/2016.
 * <p>
 * 简化的数据库操作类
 */

public class DBUtil {


    static Realm realm;

    /*
    * 保存数据信息
    *
    * */
    static public <T extends RealmObject> void saveInfo(T t) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(t);
        realm.commitTransaction();
        realm.close();
    }


    /*
    *
    * 获取密码信息
    *
    * */
    static public BasePassword getPasswordInfo(String itemTitle) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        BasePassword realmResults = realm.where(BasePassword.class).equalTo("itemTitle", itemTitle).findFirst();
        realm.commitTransaction();
        realm.close();
        return realmResults;
    }


    /*
    * 获取信用卡信息
    *
    * */
    static public CreditCard getCrediteCardInfo(String cardNumber) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        CreditCard realmResults = realm.where(CreditCard.class).equalTo("cardNumber", cardNumber).findFirst();
        realm.commitTransaction();
        realm.close();
        return realmResults;
    }




    /*
    *
    *删除信用卡信息
    *
    * */

    static public void deleteCreditCardInfo(String primaryKey) {
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
        if (primaryKey != null) {
            realm.beginTransaction();
            RealmResults<CreditCard> realmResults = realm.where(CreditCard.class).equalTo("cardNumber", primaryKey).findAll();
            realmResults.deleteFromRealm(0);
            realm.commitTransaction();
        }
    }




    /*
    * 获取身份证信息
    *
    * */

    static public IdentityCard getIdentityInfo(String idNumber) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        IdentityCard realmResults = realm.where(IdentityCard.class).equalTo("identityNumber", idNumber).findFirst();
        realm.commitTransaction();
        realm.close();
        return realmResults;

    }


    /*
    *
    * 获取网站信息
    *
    *
    * */
    static public LoginInfor LoginInfor(String accountName) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        LoginInfor realmResults = realm.where(LoginInfor.class).equalTo("accountName", accountName).findFirst();
        realm.commitTransaction();
        realm.close();
        return realmResults;
    }


    /*
    * 获取邮箱信息
    *
    *
    * */
    static public MailInfor getMailInfor(String userName) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        MailInfor realmResults = realm.where(MailInfor.class).equalTo("userName", userName).findFirst();
        realm.commitTransaction();
        realm.close();
        return realmResults;

    }

    /*
    * 获取安全笔记信息
    *
    * */

    static public SafeNote getSafenoteInfor(String itemTitle) {

        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        SafeNote realmResults = realm.where(SafeNote.class).equalTo("itemTitle", itemTitle).findFirst();
        realm.commitTransaction();
        realm.close();
        return realmResults;

    }

    static public void delteSafeNote(String key) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        SafeNote realmResults = realm.where(SafeNote.class).equalTo("itemTitle", key).findFirst();
        realmResults.deleteFromRealm();
        realm.commitTransaction();
        realm.close();
    }


    /*
    * 获取wifif信息
    *
    * */
    static public Wifiinfor getWifiInfo(String wifiName) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Wifiinfor realmResults = realm.where(Wifiinfor.class).equalTo("wifiName", wifiName).findFirst();
        realm.commitTransaction();
        realm.close();
        return realmResults;

    }


    static public void deletePassword(String itemTitle) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        BasePassword realmResults = realm.where(BasePassword.class).equalTo("itemTitle", itemTitle).findFirst();
        realmResults.deleteFromRealm();
        realm.commitTransaction();
        realm.close();
    }


    static public void deleteLoginInfor(String accountName) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        LoginInfor realmResults = realm.where(LoginInfor.class).equalTo("accountName", accountName).findFirst();
        if (realmResults != null) {
            realmResults.deleteFromRealm();
            realm.commitTransaction();
            realm.close();
        }
    }


    /*
    * 删除身份证信息
    *
    * */
    static public void delteIdentityCardInfo(String identityCardNumber) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        IdentityCard realmResults = realm.where(IdentityCard.class).equalTo("identityNumber", identityCardNumber).findFirst();
        realmResults.deleteFromRealm();
        realm.commitTransaction();
        realm.close();
    }


    static public LoginInfor getLoginInfor(String accountName) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        LoginInfor realmResults = realm.where(LoginInfor.class).equalTo("accountName", accountName).findFirst();
        realm.commitTransaction();
        realm.close();
        return realmResults;
    }


    static public void saveWifiInfo(Wifiinfor wifiinfor) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(wifiinfor);
        realm.commitTransaction();
        realm.close();
    }


    /*
    *
    * 删除wifi信息
    *
    * */
    static public void deleteWifiInfo(String wifiName) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Wifiinfor realmResults = realm.where(Wifiinfor.class).equalTo("wifiName", wifiName).findFirst();
        realmResults.deleteFromRealm();
        realm.commitTransaction();
        realm.close();


    }


    /*
    *
    * 删除邮箱信息
    *
    * */
    static public void deleteMailInfo(String userName) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Wifiinfor realmResults = realm.where(Wifiinfor.class).equalTo("wifiName", userName).findFirst();
        realmResults.deleteFromRealm();
        realm.commitTransaction();
        realm.close();
    }


    // 银行卡主类信息
    /*
    *
    *
    * 删除指定主键的数据
    *
    * 银行卡的主键是银行卡号
    *
    * */
    static public void deleteBankCard(String primaryKey) {
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
        if (primaryKey != null) {
            realm.beginTransaction();
            RealmResults<BankCard> realmResults = realm.where(BankCard.class).equalTo("cardNumber", primaryKey).findAll();
            realmResults.deleteFromRealm(0);
            realm.commitTransaction();
        }

    }

    /*
   *
   * 获取银行卡信息
   *
   * */
    static public BankCard getBankCardInfo(String bankCardNumber) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        BankCard realmResults = realm.where(BankCard.class).equalTo("cardNumber", bankCardNumber).findFirst();
        realm.commitTransaction();
        realm.close();
        return realmResults;
    }


}
