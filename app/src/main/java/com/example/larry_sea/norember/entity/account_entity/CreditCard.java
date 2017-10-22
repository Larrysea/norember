package com.example.larry_sea.norember.entity.account_entity;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by Larry-sea on 2016/9/2.
 * <p/>
 * 信用卡
 */
public class CreditCard extends RealmObject {
    String accountHolder;               //拥有者信姓名

    String cardNumber;                  //信用卡编号
    Date endTermOfValidatyDate;         //终止日期
    String remarks;                     //备注

    String country;                    //国家
    String securityNumber;             //安全码
    String creditCardName;             //信用卡名
    String address;                    //地址
    String uuid;                       //唯一id

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSecurityNumber() {
        return securityNumber;
    }

    public void setSecurityNumber(String securityNumber) {
        this.securityNumber = securityNumber;
    }

    public String getCreditCardName() {
        return creditCardName;
    }

    public void setCreditCardName(String creditCardName) {
        this.creditCardName = creditCardName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }


    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }


    public Date getEndTermOfValidatyDate() {
        return endTermOfValidatyDate;
    }

    public void setEndTermOfValidatyDate(Date endTermOfValidatyDate) {
        this.endTermOfValidatyDate = endTermOfValidatyDate;
    }


    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
