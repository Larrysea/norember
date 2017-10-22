package com.example.larry_sea.norember.entity.account_entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Larry-sea on 2016/9/2.
 * <p>
 * 数据库存储模型
 */
public class Database extends RealmObject {

    String itemTitle;        //标题
    String type;             //类型
    String server;           //服务器
    String port;             //端口
    String keyId;            //keyid
    String userName;         //用户名
    String password;         //密码
    @PrimaryKey
    String sId;              //安全标示符
    String secondAccount;    //替身
    String linkeSetting;     //连接设置
    String remarks;          //备注

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getSecondAccount() {
        return secondAccount;
    }

    public void setSecondAccount(String secondAccount) {
        this.secondAccount = secondAccount;
    }

    public String getLinkeSetting() {
        return linkeSetting;
    }

    public void setLinkeSetting(String linkeSetting) {
        this.linkeSetting = linkeSetting;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


}
