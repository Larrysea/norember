package com.example.larry_sea.norember.entity.account_entity;

import com.example.larry_sea.norember.entity.base_entity.ImagePath;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Larry-sea on 2016/9/1.
 * 软件许可
 */
public class SoftWareLicense extends RealmObject {
    String productVersion;                 //产品版本
    @PrimaryKey
    String permissionCode;                 //许可证码
    String empower;                        //授权于
    String mailAddress;                    //邮件地址
    String companyName;                    //公司名
    String downloadWevsite;                //下载页面
    String publisher;                      //发布者
    String website;                        //发布者网址
    String price;                          //价格
    String techSupportMail;                //技术支持邮件
    Date buyDate;                          //购买日期
    String orderNumber;                    //订单号
    String remarks;                        //备注
    String title;                          //标题
    RealmList<ImagePath> imagePath;                   //图片存放路径

    public RealmList<ImagePath> getImagePath() {
        return imagePath;
    }

    public void setImagePath(RealmList<ImagePath> imagePath) {
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public String getTechSupportMail() {
        return techSupportMail;
    }

    public void setTechSupportMail(String techSupportMail) {
        this.techSupportMail = techSupportMail;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDownloadWevsite() {
        return downloadWevsite;
    }

    public void setDownloadWevsite(String downloadWevsite) {
        this.downloadWevsite = downloadWevsite;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getEmpower() {
        return empower;
    }

    public void setEmpower(String empower) {
        this.empower = empower;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }
}
