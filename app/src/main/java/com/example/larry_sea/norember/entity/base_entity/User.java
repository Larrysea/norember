package com.example.larry_sea.norember.entity.base_entity;

/**
 * Created by Larry-sea on 2016/9/1.
 * <p>
 * 用户基类
 */
public class User {

    String mainPassword;               //app主密码
    String userName;                   //用户名
    String loginPassword;              //账户登录密码

    public String getMainPassword() {
        return mainPassword;
    }

    public void setMainPassword(String mainPassword) {
        this.mainPassword = mainPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
}
