package com.example.larry_sea.norember.entity.account_entity;

import com.example.larry_sea.norember.entity.base_entity.ImagePath;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Larry-sea on 2016/9/1.
 * <p/>
 * 登录信息模型
 */

public class LoginInfor extends RealmObject {


    String accountName;                   //用户名
    String url;                           //登录域名
    String webSiteName;                   //网站名
    String password;                      //密码
    String remarks;                       //备注
    int iconResId;                        //资源文件id
    String category;                      //目录
    boolean isCollection;                 //是否收藏
    RealmList<ImagePath> imagePath;       // 图片存放地址
    String uuid;                          //唯一id

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean collection) {
        isCollection = collection;
    }

    public RealmList<ImagePath> getImagePath() {
        return imagePath;
    }

    public void setImagePath(RealmList<ImagePath> imagePath) {
        this.imagePath = imagePath;
    }

    public String getAccountName() {
        if (accountName != null) {
            return accountName;
        }
        return "";
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getWebSiteName() {
        return webSiteName;
    }

    public void setWebSiteName(String webSiteName) {
        this.webSiteName = webSiteName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
