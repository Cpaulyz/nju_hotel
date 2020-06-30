package com.example.hotel.vo;


/**
 * 酒店满减金额优惠 eg 满300减100
 */
public class HotelTargetMoneyCouponVO extends CouponVO {

    private Integer hotelId;

    private Double targetMoney;       //与Coupon的targetMoney类型保持一致

    private Double discountMoney;

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Double getTargetMoney() {
        return targetMoney;
    }

    public void setTargetMoney(Double targetMoney) {
        this.targetMoney = targetMoney;
    }

    public Double getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Double discountMoney) {
        this.discountMoney = discountMoney;
    }

    public HotelTargetMoneyCouponVO(){}


}
