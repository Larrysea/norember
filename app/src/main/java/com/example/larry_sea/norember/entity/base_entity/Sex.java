package com.example.larry_sea.norember.entity.base_entity;

import io.realm.RealmObject;

/**
 * Created by Larry-sea on 2016/9/1.
 * <p>
 * 性别类
 */
public class Sex extends RealmObject {
    boolean isMale = false;        //是否是男性

    public String getSex() {
        if (isMale) {
            return "男";
        } else {
            return "女";
        }


    }

    public void setMale(boolean isMale) {
        this.isMale = isMale;
    }
}
