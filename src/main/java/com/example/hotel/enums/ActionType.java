package com.example.hotel.enums;

public enum ActionType {
    INIT("初始化"),
    EXECUTE("执行订单"),
    ANNUL("撤销订单"),
    EXCEPTION("异常订单"),
    TOPUP("充值");
    private String value;

    ActionType(String value){
        this.value=value;
    }

    @Override
    public String toString(){
        return value;
    }
}
