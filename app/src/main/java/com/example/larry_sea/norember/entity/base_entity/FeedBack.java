package com.example.larry_sea.norember.entity.base_entity;

/**
 * Created by Larry-sea on 2016/10/15.
 * <p>
 * feedback 基础类
 */

public class FeedBack {

    String feedbackContent; //反馈内容
    String contectWay;      //联系方式
    String osInfo;          //系统运行环境


    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public String getContectWay() {
        return contectWay;
    }

    public void setContectWay(String contectWay) {
        this.contectWay = contectWay;
    }

    public String getOsInfo() {
        return osInfo;
    }

    public void setOsInfo(String osInfo) {
        this.osInfo = osInfo;
    }
}
