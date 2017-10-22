package com.example.larry_sea.norember.entity.account_entity;

import io.realm.RealmObject;

/**
 * Created by Larry-sea on 2016/9/1.
 * <p/>
 * <p/>
 * 邮件信息
 */
public class MailInfor extends RealmObject {

    String userName;                     //用户名
    String password;                    //密码
    String remarks;                     //备注
    int iconResId;                      //资源文件id
    String webSite;                     //邮箱域名
    String uuid;                        //唯一id

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }


}
