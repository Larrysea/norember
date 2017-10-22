package com.example.larry_sea.norember.entity.account_entity;

import com.example.larry_sea.norember.entity.base_entity.ImagePath;
import com.example.larry_sea.norember.entity.base_entity.Sex;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Larry-sea on 2016/9/2.
 * <p>
 * <p>
 * 护照信息类
 */
public class PassPort extends RealmObject {
    String type;                //护照类型
    String issuingCountry;      //发放国家
    @PrimaryKey
    int number;                 //护照号码
    String name;                //姓名
    Sex sex;                    //性别
    String nationality;         //国籍
    String issuingInstitution;  //签发组织
    Date bornDate;              //出身日期
    String signDate;            //发放日期
    String termOfValidaty;      //有效期
    String remarks;             //备注
    RealmList<ImagePath> imagePath;        //存放图片的地址

    public RealmList<ImagePath> getImagePath() {
        return imagePath;
    }

    public void setImagePath(RealmList<ImagePath> imagePath) {
        this.imagePath = imagePath;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIssuingCountry() {
        return issuingCountry;
    }

    public void setIssuingCountry(String issuingCountry) {
        this.issuingCountry = issuingCountry;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getIssuingInstitution() {
        return issuingInstitution;
    }

    public void setIssuingInstitution(String issuingInstitution) {
        this.issuingInstitution = issuingInstitution;
    }

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }


    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getTermOfValidaty() {
        return termOfValidaty;
    }

    public void setTermOfValidaty(String termOfValidaty) {
        this.termOfValidaty = termOfValidaty;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
