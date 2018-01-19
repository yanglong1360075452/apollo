package com.wizinno.apollo.common.data;

/**
 * Created by HP on 2017/9/8.
 */
public enum SexEnum {

    Unknown(0,"未知"),
    Man(1,"男"),
    Woman(2,"女"),
    ;
    // 定义私有变量
    private Integer nCode;

    private String name;

    // 构造函数，枚举类型只能为私有
    SexEnum(Integer _nCode, String _name) {
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

    public static SexEnum valueOf(Integer code) {
        for (SexEnum sexEnum : SexEnum.values()){
            if (sexEnum.toCode().equals(code)){
                return sexEnum;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (SexEnum sexEnum : SexEnum.values()){
            if (sexEnum.toCode().equals(code)){
                return sexEnum.name;
            }
        }
        return null;
    }
}
