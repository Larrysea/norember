package com.example.larry_sea.norember.entity.account_entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Larry-sea on 2016/9/2.
 * 服务器
 */
public class Server extends RealmObject {
    String url;                                   //url
    String userName;                              //用户名
    String password;                              //密码
    String manageConsoleUrl;                      //管理控制台url
    @PrimaryKey
    String manageConsoleUserName;                 //管理控制台用户名
    String manageConsolePassword;                 //管理控制台密码
    String name;                                  //用户名
    String webSite;                               //地址
    String techWebSite;                           //技术支持网址
    String techPhone;                             //技术支持电话
    String remarks;                               //备注
    String itemTitle;                             //标题

    public String getManageConsoleUrl() {
        return manageConsoleUrl;
    }

    public void setManageConsoleUrl(String manageConsoleUrl) {
        this.manageConsoleUrl = manageConsoleUrl;
    }

    public String getManageConsoleUserName() {
        return manageConsoleUserName;
    }

    public void setManageConsoleUserName(String manageConsoleUserName) {
        this.manageConsoleUserName = manageConsoleUserName;
    }

    public String getManageConsolePassword() {
        return manageConsolePassword;
    }

    public void setManageConsolePassword(String manageConsolePassword) {
        this.manageConsolePassword = manageConsolePassword;
    }

    public String getUrl() {
        return url;

    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getTechWebSite() {
        return techWebSite;
    }

    public void setTechWebSite(String techWebSite) {
        this.techWebSite = techWebSite;
    }

    public String getTechPhone() {
        return techPhone;
    }

    public void setTechPhone(String techPhone) {
        this.techPhone = techPhone;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }
}
