package com.wizinno.apollo.app.dto;

import java.util.Date;

/**
 * Created by HP on 2017/9/21.
 */
public class UserAndNetworksDto {

    private String userId;

    private String uName;

    private String mobileNum;

    private Date timeStamp;

    private Double uploadMaximum;

    private Double uploadMinimum;

    private Double uploadAvg;

    private Double downMaximum;

    private Double downMinimum;

    private Double downAvg;

    private String appPackages;

    private String netType;

    private String operator;

    private String ssid;

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Double getUploadMaximum() {
        return uploadMaximum;
    }

    public void setUploadMaximum(Double uploadMaximum) {
        this.uploadMaximum = uploadMaximum;
    }

    public Double getUploadMinimum() {
        return uploadMinimum;
    }

    public void setUploadMinimum(Double uploadMinimum) {
        this.uploadMinimum = uploadMinimum;
    }

    public Double getUploadAvg() {
        return uploadAvg;
    }

    public void setUploadAvg(Double uploadAvg) {
        this.uploadAvg = uploadAvg;
    }

    public Double getDownMaximum() {
        return downMaximum;
    }

    public void setDownMaximum(Double downMaximum) {
        this.downMaximum = downMaximum;
    }

    public Double getDownMinimum() {
        return downMinimum;
    }

    public void setDownMinimum(Double downMinimum) {
        this.downMinimum = downMinimum;
    }

    public Double getDownAvg() {
        return downAvg;
    }

    public void setDownAvg(Double downAvg) {
        this.downAvg = downAvg;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAppPackages() {
        return appPackages;
    }

    public void setAppPackages(String appPackages) {
        this.appPackages = appPackages;
    }
}
