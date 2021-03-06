package com.wizinno.apollo.app.domain;

import com.wizinno.apollo.app.domain.model.AppRunningInfo;
import com.wizinno.apollo.app.dto.NetTestAndAppRunDto;
import com.wizinno.apollo.common.condition.AppRunningsCondition;

import java.util.List;

public interface AppRunningInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_running_info
     *
     * @mbggenerated Sat Sep 02 14:48:23 CST 2017
     */
    int deleteByPrimaryKey(String testId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_running_info
     *
     * @mbggenerated Sat Sep 02 14:48:23 CST 2017
     */
    int insert(AppRunningInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_running_info
     *
     * @mbggenerated Sat Sep 02 14:48:23 CST 2017
     */
    AppRunningInfo selectByPrimaryKey(String testId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_running_info
     *
     * @mbggenerated Sat Sep 02 14:48:23 CST 2017
     */
    List<AppRunningInfo> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_running_info
     *
     * @mbggenerated Sat Sep 02 14:48:23 CST 2017
     */
    int updateByPrimaryKey(AppRunningInfo record);

    List<NetTestAndAppRunDto> getAppRunnings(AppRunningsCondition con);

    Long getAppRunningsTotal(AppRunningsCondition con);

    Integer getAppRunningsByTestId(String testId);

    List<String> getAppRunningsByTestIdAndFrontApp(String testId);
}