package com.wizinno.apollo.common.condition;

import com.wizinno.apollo.common.data.PageCondition;

/**
 * Created by HP on 2017/9/10.
 */
public class AppRunningsCondition extends PageCondition{

    private String uName;

    private String mobileNum;

    private String operator;

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
