package com.wizinno.apollo.common.data;

/**
 * Created by HP on 2017/9/4.
 */
public enum StatusEnum {

    Disabled(0,"非活动"),
    Actived(1,"活动"),
    ;
    // 定义私有变量
    private Integer nCode;

    private String name;

    // 构造函数，枚举类型只能为私有
    StatusEnum(Integer _nCode, String _name) {
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

    public static StatusEnum valueOf(Integer code) {
        for (StatusEnum statusEnum : StatusEnum.values()){
            if (statusEnum.toCode().equals(code)){
                return statusEnum;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (StatusEnum statusEnum : StatusEnum.values()){
            if (statusEnum.toCode().equals(code)){
                return statusEnum.name;
            }
        }
        return null;
    }
}
