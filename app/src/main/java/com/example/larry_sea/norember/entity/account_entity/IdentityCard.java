package com.example.larry_sea.norember.entity.account_entity;

import com.example.larry_sea.norember.entity.base_entity.ImagePath;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Larry-sea on 2016/9/2.
 * <p>
 * <p>
 * 身份证信息
 */
public class IdentityCard extends RealmObject {
    String name;                        //姓名
    String bornData;                      //出生日期
    String address;                     //地址
    String identityNumber;              //身份证号
    String issuingAuthority;            //签发机关
    String startTermOfDate;             //生效日期
    String endTermOfDate;                 //终止日期
    String country;                     //国家
    boolean sex;                        //sex true代表男性 false为女性
    RealmList<ImagePath> imagePath;     //图片存放路径
    String uuid;                        //唯一id
    public String getCountry() {
        return country;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public RealmList<ImagePath> getImagePath() {
        return imagePath;
    }

    public void setImagePath(RealmList<ImagePath> imagePath) {
        this.imagePath = imagePath;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getIssuingAuthority() {
        return issuingAuthority;
    }

    public void setIssuingAuthority(String issuingAuthority) {
        this.issuingAuthority = issuingAuthority;
    }

    public String getBornData() {
        return bornData;
    }

    public void setBornData(String bornData) {
        this.bornData = bornData;
    }

    public String getStartTermOfDate() {
        return startTermOfDate;
    }

    public void setStartTermOfDate(String startTermOfDate) {
        this.startTermOfDate = startTermOfDate;
    }

    public String getEndTermOfDate() {
        return endTermOfDate;
    }

    public void setEndTermOfDate(String endTermOfDate) {
        this.endTermOfDate = endTermOfDate;
    }
}
