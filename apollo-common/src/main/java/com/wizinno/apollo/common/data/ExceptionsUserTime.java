package com.wizinno.apollo.common.data;

/**
 * Created by HP on 2017/9/19.
 */
public enum ExceptionsUserTime {

    NinetyMinutes(1,"90"),

    ;
    // 定义私有变量
    private Integer nCode;

    private String name;

    // 构造函数，枚举类型只能为私有
    ExceptionsUserTime(Integer _nCode, String _name) {
        this.nCode = _nCode;
        this.name = _name;
    }

    public Integer toCode(){
        return this.nCode;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static ExceptionsUserTime valueOf(Integer code) {
        for (ExceptionsUserTime exceptionsUserTime : ExceptionsUserTime.values()){
            if (exceptionsUserTime.toCode().equals(code)){
                return exceptionsUserTime;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (ExceptionsUserTime exceptionsUserTime : ExceptionsUserTime.values()){
            if (exceptionsUserTime.toCode().equals(code)){
                return exceptionsUserTime.name;
            }
        }
        return null;
    }
}
