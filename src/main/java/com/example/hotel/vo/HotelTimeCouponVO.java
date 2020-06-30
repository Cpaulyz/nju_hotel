package com.example.hotel.vo;

import java.time.LocalDateTime;

/**
 * 酒店节日优惠 eg 情人节打8折
 * @author Barney
 * @Date 2020-04-18
 * */

public class HotelTimeCouponVO extends CouponVO{

    private Integer hotelId;

    private Double discount;            //折扣

    private LocalDateTime startTime;    //可用时间

    private LocalDateTime endTime;      //失效时间

    public Integer getHotelId() {
        return hotelId;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public HotelTimeCouponVO(){}
}