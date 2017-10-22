package com.example.larry_sea.norember.entity.base_entity;

import io.realm.RealmObject;

/**
 * Created by Larry-sea on 2016/9/13.
 * <p>
 * 存储展现的基础模型
 * <p>
 * 例如主   assignfragment  中至展示的元素 以及每个子项目中该展示的元素
 */
public class BaseStorageItemEntity extends RealmObject {
    String iconPath;            //展示图片的地址
    int storageType;            //存储类型
    String accountName;         //存储的账户名
    String title;               //标题
    String subTitle;

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public int getStorageType() {
        return storageType;
    }

    public void setStorageType(int storageType) {
        this.storageType = storageType;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
