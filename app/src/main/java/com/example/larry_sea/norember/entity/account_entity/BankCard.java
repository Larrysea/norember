package com.example.larry_sea.norember.entity.account_entity;

import android.util.Log;

import com.example.larry_sea.norember.entity.base_entity.ImagePath;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Larry-sea on 2016/9/1.
 * <p/>
 * 银行卡
 */
public class BankCard extends RealmObject {


    String bankName;                //银行名称
    String country;                 //国家
    String accoutHolder;            //账户持有人
    String bicOrswift;              //bic/swift代码
    String iban;                    //国家银行账号
    String cardNumber;              //银行卡号
    RealmList<ImagePath> imagePath; //银行卡的照片
    String password;                //银行卡密码
    String ibanName;                //国家银行账户名称
    String uuid;                    //账户为一id

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getIbanName() {
        return ibanName;
    }

    public void setIbanName(String ibanName) {
        this.ibanName = ibanName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }


    public RealmList<ImagePath> getImagePath() {
        return imagePath;
    }

    public void setImagePath(RealmList<ImagePath> imagePath) {
        this.imagePath = imagePath;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAccoutHolder() {
        return accoutHolder;
    }

    public void setAccoutHolder(String accoutHolder) {
        this.accoutHolder = accoutHolder;
    }

    public String getBicOrswift() {
        return bicOrswift;
    }

    public void setBicOrswift(String bicOrswift) {
        this.bicOrswift = bicOrswift;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getTitle() {
        if (bankName != null) {
            Log.e("bank card", "not null");
            return bankName;
        } else {
            Log.e("bank card", "null");
            return null;
        }
    }

    public String getSubTitle() {
        if (cardNumber != null)
            return cardNumber;
        else
            return null;
    }


}
