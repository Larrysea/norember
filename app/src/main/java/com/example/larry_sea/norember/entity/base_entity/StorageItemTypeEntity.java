package com.example.larry_sea.norember.entity.base_entity;

import java.io.Serializable;

/**
 * Created by Larry-sea on 2016/9/15.
 * 存储的大类的信息实体
 * <p>
 * main fragment 界面中的数据模型
 */
public class StorageItemTypeEntity implements Serializable {
    String itemTitle;   //大类型的数据标题
    int iconPath;       //图标地址
    int amount;        // 存储的数量
    int type;          //类型

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public int getIconPath() {
        return iconPath;
    }

    public void setIconPath(int iconPath) {
        this.iconPath = iconPath;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
