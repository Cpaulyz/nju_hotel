package com.example.hotel.vo;

public class HotelOfMultiCouponVO extends CouponVO {

    private Integer hotelId;

    private Integer multiThreshold;

    private Double discount;            //折扣

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getMultiThreshold() {
        return multiThreshold;
    }

    public void setMultiThreshold(Integer multiThreshold) {
        this.multiThreshold = multiThreshold;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public HotelOfMultiCouponVO(){}
}
