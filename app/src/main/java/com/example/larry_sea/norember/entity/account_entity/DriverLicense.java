package com.example.larry_sea.norember.entity.account_entity;

import com.example.larry_sea.norember.entity.base_entity.ImagePath;
import com.example.larry_sea.norember.entity.base_entity.Sex;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Larry-sea on 2016/9/1.
 * <p>
 * 驾照信息
 */
public class DriverLicense extends RealmObject {

    @PrimaryKey
    String certificateId;                               //id
    String name;                                       //姓名
    Sex sex;                                           //性别
    String nationNality;                               //国籍
    String address;                                    //地址
    Date bornDate;                                     //出生日期
    String driverType;                                 //驾照类型
    Date endDate;                                      //终止日期
    RealmList<ImagePath> imagePath;                   //图片存放路径


    public RealmList<ImagePath> getImagePath() {
        return imagePath;
    }

    public void setImagePath(RealmList<ImagePath> imagePath) {
        this.imagePath = imagePath;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getNationNality() {
        return nationNality;
    }

    public void setNationNality(String nationNality) {
        this.nationNality = nationNality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }


    public String getDriverType() {
        return driverType;
    }

    public void setDriverType(String driverType) {
        this.driverType = driverType;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
