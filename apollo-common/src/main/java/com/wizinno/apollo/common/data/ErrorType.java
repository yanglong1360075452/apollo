package com.wizinno.apollo.common.data;

/**
 * Created by HP on 2017/9/4.
 */
public enum ErrorType {

    Normal(0,"正常"),
    BrokenNet(1,"断网"),
    WeakNet(2,"弱网"),
    ;
    // 定义私有变量
    private Integer nCode;

    private String name;

    // 构造函数，枚举类型只能为私有
    ErrorType(Integer _nCode, String _name) {
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

    public static ErrorType valueOf(Integer code) {
        for (ErrorType errorType : ErrorType.values()){
            if (errorType.toCode().equals(code)){
                return errorType;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (ErrorType errorType : ErrorType.values()){
            if (errorType.toCode().equals(code)){
                return errorType.name;
            }
        }
        return null;
    }
}
