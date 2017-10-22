package com.example.larry_sea.norember.entity.base_entity;

import java.io.Serializable;

/**
 * Created by Larry-sea on 9/24/2016.
 * 存储小类的信息实体
 */

public class SmallTypeEntity implements Serializable {
    String typeName;         //类型名
    int iconSource;         //icon资源


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getIconSource() {
        return iconSource;
    }

    public void setIconSource(int iconSource) {
        this.iconSource = iconSource;
    }

}
