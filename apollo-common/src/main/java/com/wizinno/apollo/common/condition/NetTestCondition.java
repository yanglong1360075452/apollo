package com.wizinno.apollo.common.condition;

import com.wizinno.apollo.common.data.PageCondition;

import java.util.Date;

/**
 * Created by HP on 2017/9/4.
 */
public class NetTestCondition extends PageCondition {

    private String filter;

    private Date startTime;

    private Date endTime;

    private String uName;

    private Integer testEvent;

    public Integer getTestEvent() {
        return testEvent;
    }

    public void setTestEvent(Integer testEvent) {
        this.testEvent = testEvent;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
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
}
