package com.wizinno.apollo.common.exception;

import java.util.HashMap;
import java.util.Map;

public class CustomExceptionCode {
    public static final Integer AccessDenied = 1;
    public static final Integer AuthFailed = 2;
    public static final Integer UsernameOrPasswordError = 3;
    public static final Integer ErrorParam = 4;
    public static final Integer CaptchaError=5;
    public static final Integer UserNotExist=6;
    public static final Integer WrongPassword=7;
    public static final Integer UsernameExist=8;
    public static final Integer PhoneExist=9;
    public static final Integer PasswordMismatch=10;
    public static final Integer ExportData=11;
    public static final Integer NetTestLogNotExist=12;
    public static final Integer DateRepeat=13;
    public static final Integer ImgSize=14;



    private final static Map<Integer, String> errorMap = new HashMap<>();


    static {
        errorMap.put(1, "权限不足");
        errorMap.put(2, "认证失败");
        errorMap.put(3, "用户名或密码错误");
        errorMap.put(4, "参数错误");
        errorMap.put(5, "验证码错误");
        errorMap.put(6, "用户不存在，请注册");
        errorMap.put(7, "密码错误");
        errorMap.put(8, "用户名存在");
        errorMap.put(9, "手机号码存在");
        errorMap.put(10, "输入的密码不一致");
        errorMap.put(11, "导出数据超过6万");
        errorMap.put(12, "网络质量不存在");
        errorMap.put(13, "数据重复");
        errorMap.put(14, "ImgSize0KB");

    }

    public static String getReasonByCode(Integer code, String defaultReason){
        if(errorMap.containsKey(code)){
            return errorMap.get(code);
        }else{
            return defaultReason;
        }
    }
}
