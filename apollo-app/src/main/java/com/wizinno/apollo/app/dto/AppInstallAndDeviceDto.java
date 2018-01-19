package com.wizinno.apollo.app.dto;

import java.util.Date;

/**
 * Created by HP on 2017/9/22.
 */
public class AppInstallAndDeviceDto {

    private Date checkTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_install_info.user_id
     *
     * @mbggenerated Fri Sep 15 11:47:53 CST 2017
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_install_info.dev_id
     *
     * @mbggenerated Fri Sep 15 11:47:53 CST 2017
     */
    private String devId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_install_info.app_package
     *
     * @mbggenerated Fri Sep 15 11:47:53 CST 2017
     */
    private String appPackage;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_install_info.app_name
     *
     * @mbggenerated Fri Sep 15 11:47:53 CST 2017
     */
    private String appName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_install_info.app_ver
     *
     * @mbggenerated Fri Sep 15 11:47:53 CST 2017
     */
    private String appVer;

    private String uName;

    private String mobileNum;

    private String devImei;

    private Integer appInstallTotal;

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVer() {
        return appVer;
    }

    public void setAppVer(String appVer) {
        this.appVer = appVer;
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

    public String getDevImei() {
        return devImei;
    }

    public void setDevImei(String devImei) {
        this.devImei = devImei;
    }

    public Integer getAppInstallTotal() {
        return appInstallTotal;
    }

    public void setAppInstallTotal(Integer appInstallTotal) {
        this.appInstallTotal = appInstallTotal;
    }
}