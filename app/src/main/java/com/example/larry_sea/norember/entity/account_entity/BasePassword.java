package com.example.larry_sea.norember.entity.account_entity;

import com.example.larry_sea.norember.entity.base_entity.ImagePath;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Larry-sea on 2016/9/1.
 * <p>
 * 密码基类
 */

public class BasePassword extends RealmObject {

    protected String itemTitle;                    //密码item标题
    protected String password;                     //密码
    protected String remarks;                      //备注
    protected int iconResId;                       //资源文件id
    protected RealmList<ImagePath> imagePath;      // 图片存放地址
    protected String category;                     //目录

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public RealmList<ImagePath> getImagePath() {
        return imagePath;
    }

    public void setImagePath(RealmList<ImagePath> imagePath) {
        this.imagePath = imagePath;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public String getItemTitle() {
        return itemTitle;
    }


    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
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


    public String getTitle() {
        return itemTitle;
    }

}
