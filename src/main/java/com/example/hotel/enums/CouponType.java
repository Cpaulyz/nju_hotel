package com.example.hotel.enums;

public enum CouponType {

    Multi(0),      //多间优惠
    Festival(1),   //节日优惠
    Target(2);     //满减优惠

    private Integer value;

    CouponType(Integer value){
        this.value=value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
