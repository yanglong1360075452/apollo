package com.wizinno.apollo.common.data;

/**
 * Created by HP on 2017/9/4.
 */
public enum TestEvent {

    NetConnect(1,"网络接通"),
    NatmMlfunction(2,"网络故障"),
    SignalChange(3,"信号变化"),
    BaseStation(4,"基站切换"),
    ManualTrigger(5,"手动触发"),
    SelfTiming(6,"自动定时"),
    NetStation(7,"网络切换"),
    ;
    // 定义私有变量
    private Integer nCode;

    private String name;

    // 构造函数，枚举类型只能为私有
    TestEvent(Integer _nCode, String _name) {
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

    public static TestEvent valueOf(Integer code) {
        for (TestEvent testEvent : TestEvent.values()){
            if (testEvent.toCode().equals(code)){
                return testEvent;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (TestEvent testEvent : TestEvent.values()){
            if (testEvent.toCode().equals(code)){
                return testEvent.name;
            }
        }
        return null;
    }
}
