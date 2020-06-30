package com.example.hotel.enums;

public enum OrderStateType {

    Ordered("已下单"),
    Executed("已执行"),
    Annulled("已撤销"),
    Evaluated("已评价"),
    Exception("异常");
    private String value;

    OrderStateType(String value){
        this.value=value;
    }

    @Override
    public String toString(){
        return value;
    }
}
