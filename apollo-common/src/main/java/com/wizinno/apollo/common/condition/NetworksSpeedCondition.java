package com.wizinno.apollo.common.condition;

import com.wizinno.apollo.common.data.PageCondition;

import java.util.Date;
import java.util.zip.DataFormatException;

/**
 * Created by HP on 2017/9/21.
 */
public class NetworksSpeedCondition extends PageCondition {

    private String uName;

    private String mobileNum;

    private String operator;

    private Date startTime;

    private Date endTime;

    private Double uploadMaximum;

    private Double downMaximum;

    public Double getUploadMaximum() {
        return uploadMaximum;
    }

    public void setUploadMaximum(Double uploadMaximum) {
        this.uploadMaximum = uploadMaximum;
    }

    public Double getDownMaximum() {
        return downMaximum;
    }

    public void setDownMaximum(Double downMaximum) {
        this.downMaximum = downMaximum;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
