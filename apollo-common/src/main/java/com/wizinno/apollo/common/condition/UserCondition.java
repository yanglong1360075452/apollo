package com.wizinno.apollo.common.condition;

import com.wizinno.apollo.common.data.PageCondition;

/**
 * Created by HP on 2017/9/8.
 */
public class UserCondition extends PageCondition {

    private String uName;

    private String mobileNum;

    private String operator;

    private String exceptionsTime;

    public String getExceptionsTime() {
        return exceptionsTime;
    }

    public void setExceptionsTime(String exceptionsTime) {
        this.exceptionsTime = exceptionsTime;
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
