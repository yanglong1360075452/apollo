package com.wizinno.apollo.app.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by HP on 2017/9/10.
 */
public class NetTestAndAppRunDto {

    private String testId;

    private Date checkTime;

    private Integer testEvent;
    private String testEventDesc;

    private String userName;

    private String mobileNum;

    private String deviceImei;

    private Integer appRunTotal;

    private String appNameFront;
    private String appName;

    private Integer isFrontApp;

    private List<String> appNames;

    public String getAppNameFront() {
        return appNameFront;
    }

    public void setAppNameFront(String appNameFront) {
        this.appNameFront = appNameFront;
    }

    public List<String> getAppNames() {
        return appNames;
    }

    public void setAppNames(List<String> appNames) {
        this.appNames = appNames;
    }

    public Integer getAppRunTotal() {
        return appRunTotal;
    }

    public void setAppRunTotal(Integer appRunTotal) {
        this.appRunTotal = appRunTotal;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Integer getTestEvent() {
        return testEvent;
    }

    public void setTestEvent(Integer testEvent) {
        this.testEvent = testEvent;
    }

    public String getTestEventDesc() {
        return testEventDesc;
    }

    public void setTestEventDesc(String testEventDesc) {
        this.testEventDesc = testEventDesc;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getDeviceImei() {
        return deviceImei;
    }

    public void setDeviceImei(String deviceImei) {
        this.deviceImei = deviceImei;
    }


    public Integer getIsFrontApp() {
        return isFrontApp;
    }

    public void setIsFrontApp(Integer isFrontApp) {
        this.isFrontApp = isFrontApp;
    }

}
