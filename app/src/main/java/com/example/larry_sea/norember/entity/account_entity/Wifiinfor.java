package com.example.larry_sea.norember.entity.account_entity;

import io.realm.RealmObject;

/**
 * Created by Larry-sea on 2016/9/1.
 * <p>
 * wifi信息
 */
public class Wifiinfor extends RealmObject {
    String wifiName;                           //wifi名称
    String wifiPassword;                       //wifi密码
    String wifiIp;                             //wifiType Ip
    String remarks;                            //备注
    String adminPassword;                      //wifi管理员密码
    String adminAccount;                       //wifi管理员账户
    String internetProviderAccount;            //网络提供商提供的账户
    String internetProviderPassword;           //网络提供商提供的密码

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    String uuid;                               //唯一id


    public String getInternetProviderPassword() {
        return internetProviderPassword;
    }

    public void setInternetProviderPassword(String internetProviderPassword) {
        this.internetProviderPassword = internetProviderPassword;
    }

    public String getInternetProviderAccount() {

        return internetProviderAccount;
    }

    public void setInternetProviderAccount(String internetProviderAccount) {
        this.internetProviderAccount = internetProviderAccount;
    }

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public String getAdminAccount() {
        return adminAccount;
    }

    public void setAdminAccount(String adminAccount) {
        this.adminAccount = adminAccount;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }


    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    public String getWifiIp() {
        return wifiIp;
    }

    public void setWifiIp(String wifiIp) {
        this.wifiIp = wifiIp;
    }

    public String getWifiPassword() {
        return wifiPassword;
    }

    public void setWifiPassword(String wifiPassword) {
        this.wifiPassword = wifiPassword;
    }

}
