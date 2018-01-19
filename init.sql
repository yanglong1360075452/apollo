/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50713
Source Host           : localhost:3306
Source Database       : pathcloud_wechat

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2017-05-24 14:44:48
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id`     VARCHAR(255) NOT NULL,
  `mobile_num`  VARCHAR(30)  NOT NULL UNIQUE, #手机号码
  `operator`    VARCHAR(100) DEFAULT NULL, #运营商
  `net_system`  VARCHAR(100) DEFAULT NULL, #网络制式
  `password`    VARCHAR(100) NOT NULL, #密码
  `u_name`      VARCHAR(100) DEFAULT NULL, #姓名
  `u_sex`       INT(1)       DEFAULT 0, #性别 0未知 1男 2女
  `u_address`   VARCHAR(255) DEFAULT NULL, #地址
  `u_birthdate` DATE         DEFAULT NULL, #出生年月
  `u_reg_date`  DATETIME     DEFAULT CURRENT_TIMESTAMP, #注册时间
  `state`       INT(1)       DEFAULT 1, #状态1 actived ,0 disabled
  PRIMARY KEY (`user_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `dev_id`          VARCHAR(255) NOT NULL,
  `dev_imei`        VARCHAR(30)  NOT NULL, #手机imei
  `dev_brand`       VARCHAR(100) DEFAULT NULL, #手机品牌
  `dev_model`       VARCHAR(100) DEFAULT NULL, #设备型号
  `android_api_ver` VARCHAR(100) DEFAULT NULL, #安卓版本
  `factory_os`      VARCHAR(100) DEFAULT NULL, #厂商定制os
  `factory_os_ver`  VARCHAR(100) DEFAULT NULL, #厂商定制os版本
  `d_mobileNum`     VARCHAR(255) DEFAULT NULL, #关联手机号
  `u_name`          VARCHAR(100) DEFAULT NULL, #用户名
  `d_loginTime`     DATETIME     DEFAULT NULL, #用户手机号首次登录绑定时间
  `state`           INT(1)       DEFAULT 1, #状态1 actived ,0 disabled
  PRIMARY KEY (`dev_id`, `dev_imei`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE device
  ADD COLUMN u_name VARCHAR(100);


DROP TABLE IF EXISTS `net_test_log`;
CREATE TABLE `net_test_log` (
  `test_id`               VARCHAR(255) NOT NULL,
  `test_time`             DATETIME     NOT NULL, #测试时间
  `test_event`            INT(1)       NOT NULL, #测试事件 1:网络接通，2:网络故障, 3:信号变化，4:基站切换，5:手动触发
  `user_name`             VARCHAR(30)  NOT NULL, #用户姓名
  `mobile_num`            VARCHAR(30)  NOT NULL, #手机号码
  `device_imei`           VARCHAR(30)  NOT NULL, #手机IMEI
  `net_type`              VARCHAR(30)  NOT NULL, #网络类型
  `operator`              VARCHAR(100) NOT NULL, #运营商
  `ip_addr`               VARCHAR(30)  NOT NULL, #IP地址
  `ssid`                  VARCHAR(30)  DEFAULT NULL, #WIFI SSID
  `gps`                   VARCHAR(255) DEFAULT NULL, #GPS位置
  `mcc`                   VARCHAR(100) DEFAULT NULL, #移动国家代码MCC
  `mnc`                   VARCHAR(100) DEFAULT NULL, #移动网络号码MNC
  `lac`                   VARCHAR(100) DEFAULT NULL, #位置区域码LAC
  `cid`                   VARCHAR(100) DEFAULT NULL, #基站编号CID
  `bsss`                  VARCHAR(100) DEFAULT NULL, #基站信号强度BSSS
  `neighbor_station_info` VARCHAR(255) DEFAULT NULL, #周围基站信息

  `trans_speed_up`        BIGINT(100)  DEFAULT NULL, #上传速度
  `trans_speed_down`      BIGINT(100)  DEFAULT NULL, #下载速度
  `pakage_loss_prob`      VARCHAR(100) DEFAULT NULL, #丢包率
  `ave_trans_delay`       BIGINT(100)  DEFAULT NULL, #平均延时
  `trans_jitter_p`        BIGINT(100)  DEFAULT NULL, #抖动+
  `trans_jitter_n`        BIGINT(100)  DEFAULT NULL, #抖动-
  `err_type`              INT(1)       DEFAULT NULL, #错误类型0:正常，1:断网，2:弱网
  `manual_click`          INT(30)      DEFAULT NULL, #手动触发冗余数
  `screen_shap`           VARCHAR(255) DEFAULT NULL, #截屏地址
  `dev_netspeed`          BIGINT(100)  DEFAULT NULL, #手机系统网速
  `describe`              VARCHAR(255) DEFAULT NULL, #吐槽内容
  `is_front_app` INT(1)       DEFAULT NULL, #是否是前台
  `app_name`     VARCHAR(100) DEFAULT NULL, #App应用名
  PRIMARY KEY (`test_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE net_test_log
  MODIFY COLUMN pakage_loss_prob VARCHAR(100);

ALTER TABLE net_test_log
  ADD COLUMN `describe` VARCHAR(255);


UPDATE net_test_log
SET screen_shap = NULL
WHERE screen_shap = "无";


DROP TABLE IF EXISTS `app_running_info`;
CREATE TABLE `app_running_info` (
  `test_id`      VARCHAR(255) NOT NULL,
  `check_time`   DATETIME     NOT NULL, #检测日期
  `user_id`      VARCHAR(255) NOT NULL, #UserID
  `dev_id`       VARCHAR(255) NOT NULL, #DeviceID
  `app_name`     VARCHAR(100) DEFAULT NULL, #App应用名
  `app_package`  VARCHAR(100) NOT NULL, #App应用包名
  `app_ver`      VARCHAR(100) DEFAULT NULL, #App应用版本号
  `is_front_app` INT(1)       DEFAULT NULL, #是否是前台
  PRIMARY KEY (`test_id`, `user_id`, `dev_id`, `app_package`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `app_install_info`;
CREATE TABLE `app_install_info` (
  `check_time`  DATETIME     NOT NULL, #检测日期
  `user_id`     VARCHAR(255) NOT NULL, #UserID
  `dev_id`      VARCHAR(255) NOT NULL, #DeviceID
  `app_name`    VARCHAR(100) DEFAULT NULL, #App应用名
  `app_package` VARCHAR(100) NOT NULL, #App应用包名
  `app_ver`     VARCHAR(100) DEFAULT NULL, #App应用版本号
  PRIMARY KEY (`check_time`, `user_id`, `dev_id`, `app_package`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `network_speed`;
CREATE TABLE `network_speed` (
  `user_id`        VARCHAR(255) NOT NULL,
  `time_stamp`     DATETIME     NOT NULL, #检测日期
  `net_type`              VARCHAR(30)  NOT NULL, #网络类型
  `operator`              VARCHAR(100) NOT NULL, #运营商
  `ssid`                  VARCHAR(30)  DEFAULT NULL, #WIFI SSID
  `upload_maximum` DOUBLE       NOT NULL, #上传最大值
  `upload_minimum` DOUBLE       NOT NULL, #上传最小值
  `upload_avg`     DOUBLE       NOT NULL, #上传平均值
  `down_maximum`   DOUBLE       NOT NULL, #下载最大值
  `down_minimum`   DOUBLE       NOT NULL, #下载最小值
  `down_avg`       DOUBLE       NOT NULL, #下载平均值
  `app_packages`   VARCHAR(100) NOT NULL, #前台app包名
  PRIMARY KEY (`user_id`, `time_stamp`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE network_speed
  MODIFY COLUMN time_stamp DATETIME(6) NOT NULL
  COMMENT '检测日期';
ALTER TABLE app_running_info
  MODIFY COLUMN check_time DATETIME(6) NOT NULL
  COMMENT '检测日期';
ALTER TABLE app_install_info
  MODIFY COLUMN check_time DATETIME(6) NOT NULL
  COMMENT '检测日期';


  ALTER TABLE network_speed
  ADD COLUMN `net_type` VARCHAR(30);

  ALTER TABLE network_speed
  ADD COLUMN `operator` VARCHAR(100);

    ALTER TABLE network_speed
  ADD COLUMN `ssid` VARCHAR(30);


  ##网络质量加前台属性
  ALTER TABLE net_test_log
  ADD COLUMN `is_front_app` int(1);

##网络质量加前台app
  ALTER TABLE net_test_log
  ADD COLUMN `app_name` VARCHAR(100);




/*
DROP TEMPORARY TABLE IF EXISTS `tmp_table`;
CREATE TEMPORARY TABLE `tmp_table` (
  `phone`        VARCHAR(100) NOT NULL,
  `code`    VARCHAR(11)       NOT NULL, #验证码
  `type`  INT(11)      NOT NULL, #类型
  `time_stamp`     DATETIME     NOT NULL, #检测日期

  PRIMARY KEY (`phone`)
)
*/

DROP  TABLE IF EXISTS `tmp_table`;
CREATE  TABLE `tmp_table` (
  `phone`        VARCHAR(100) NOT NULL,
  `code`    VARCHAR(11)       NOT NULL, #验证码
  `type`  INT(11)      NOT NULL, #类型
  `time_stamp`     DATETIME     NOT NULL, #检测日期

  PRIMARY KEY (`phone`)
)