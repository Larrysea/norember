package com.example.larry_sea.norember.entity.account_entity;

import com.example.larry_sea.norember.entity.base_entity.ImagePath;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Larry-sea on 2016/9/2.
 * <p>
 * <p>
 * 会员信息
 */
public class MemberCard extends RealmObject {
    String itemTitle;                         //标题
    String companyName;                       //公司名
    String phone;                             //电话号
    String memberName;                        //会员名
    @PrimaryKey
    String memberNumber;                      //会员编号
    String memberStartDate;                   //会员生效日期
    String memberEndData;                     //会员终止日期
    String memberPassword;                    //会员密码
    String remarks;                           //备注信息
    RealmList<ImagePath> picturePath;                     //照片存储路径

    public RealmList<ImagePath> getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(RealmList<ImagePath> picturePath) {
        this.picturePath = picturePath;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(String memberNumber) {
        this.memberNumber = memberNumber;
    }

    public String getMemberStartDate() {
        return memberStartDate;
    }

    public void setMemberStartDate(String memberStartDate) {
        this.memberStartDate = memberStartDate;
    }

    public String getMemberEndData() {
        return memberEndData;
    }

    public void setMemberEndData(String memberEndData) {
        this.memberEndData = memberEndData;
    }

    public String getMemberPassword() {
        return memberPassword;
    }

    public void setMemberPassword(String memberPassword) {
        this.memberPassword = memberPassword;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
