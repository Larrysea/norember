package com.example.larry_sea.norember.entity.base_entity;

import io.realm.RealmObject;

/**
 * Created by Larry-sea on 2016/9/17.
 * <p>
 * <p>
 * 图片路径存放实体类
 */
public class ImagePath extends RealmObject {
    String firstPath;
    String sendcondPath;
    String thirdPath;

    public String getSendcondPath() {
        return sendcondPath;
    }

    public void setSendcondPath(String sendcondPath) {
        this.sendcondPath = sendcondPath;
    }

    public String getThirdPath() {
        return thirdPath;
    }

    public void setThirdPath(String thirdPath) {
        this.thirdPath = thirdPath;
    }

    public String getFirstPath() {

        return firstPath;
    }

    public void setFirstPath(String firstPath) {
        this.firstPath = firstPath;
    }
}
