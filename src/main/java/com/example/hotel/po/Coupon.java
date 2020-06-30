package com.example.hotel.po;
import com.example.hotel.enums.CouponType;
import com.example.hotel.vo.HotelOfMultiCouponVO;
import com.example.hotel.vo.HotelTimeCouponVO;
import com.example.hotel.vo.HotelTargetMoneyCouponVO;
import java.time.LocalDateTime;
import java.util.Objects;


public class Coupon {
    /**
     * 优惠券id
     */
    private int id;
    /**
     * 优惠券描述
     */
    private String description;

    /**
     * 如果为0 代表是网站推出的优惠
     */
    private Integer hotelId;

    /**
     * 优惠券类型 0多间特惠 1节日优惠 2满减优惠
     */
    private CouponType couponType;
    /**
     * 优惠券名称
     */
    private String couponName;
    /**
     * 满减优惠的使用门槛
     */
    private double targetMoney;        // 都改成了double 但数据库里的类型是int 可输入小数 但会自动四舍五入

    /**
     * 多件优惠的使用门槛
     * */
    private Integer multiThreshold;

    /**
     * 折扣
     */
    private double discount;
    /**
     * 优惠券优惠金额
     */
    private double discountMoney;
    /**
     * 可用时间
     */
    private LocalDateTime startTime;
    /**
     * 失效时间
     */
    private LocalDateTime endTime;

    /**
     * 优惠券状态 是否已经失效 1可用 0失效
     */
    private Integer status;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public double getTargetMoney() {
        return targetMoney;
    }

    public void setTargetMoney(double targetMoney) {
        this.targetMoney = targetMoney;
    }

    public Integer getMultiThreshold() {
        return multiThreshold;
    }

    public void setMultiThreshold(Integer multiThreshold) {
        this.multiThreshold = multiThreshold;
    }

    public double getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(double discountMoney) {
        this.discountMoney = discountMoney;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public CouponType getCouponType() {
        return couponType;
    }

    public void setCouponType(CouponType couponType) {
        this.couponType = couponType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Coupon(){}

    public Coupon(HotelTargetMoneyCouponVO hotelTargetMoneyCouponVO) {
        //在最一开始add的时候调用 也有id 但是是随便给的 在update调用的时候才是有真正正常的id的
        this.setId(hotelTargetMoneyCouponVO.getId());
        this.setCouponName(hotelTargetMoneyCouponVO.getName());
        this.setDescription(hotelTargetMoneyCouponVO.getDescription());
        this.setCouponType(hotelTargetMoneyCouponVO.getType());
        this.setTargetMoney(hotelTargetMoneyCouponVO.getTargetMoney());
        this.setHotelId(hotelTargetMoneyCouponVO.getHotelId());
        this.setDiscountMoney(hotelTargetMoneyCouponVO.getDiscountMoney());
        this.setStatus(hotelTargetMoneyCouponVO.getStatus());
    }

    public Coupon(HotelTimeCouponVO hotelTimeCouponVO){
        //在最一开始add的时候调用 也有id 但是是随便给的 在update调用的时候才是有真正正常的id的
        this.setId(hotelTimeCouponVO.getId());
        this.setCouponName(hotelTimeCouponVO.getName());
        this.setDescription(hotelTimeCouponVO.getDescription());
        this.setCouponType(hotelTimeCouponVO.getType());
        this.setHotelId(hotelTimeCouponVO.getHotelId());
        this.setDiscount(hotelTimeCouponVO.getDiscount());
        this.setStartTime(hotelTimeCouponVO.getStartTime());
        this.setEndTime(hotelTimeCouponVO.getEndTime());
        this.setStatus(hotelTimeCouponVO.getStatus());
    }

    public Coupon(HotelOfMultiCouponVO hotelOfMultiCouponVO){
        this.setId(hotelOfMultiCouponVO.getId());
        this.setCouponName(hotelOfMultiCouponVO.getName());
        this.setDescription(hotelOfMultiCouponVO.getDescription());
        this.setCouponType(hotelOfMultiCouponVO.getType());
        this.setHotelId(hotelOfMultiCouponVO.getHotelId());
        this.setMultiThreshold(hotelOfMultiCouponVO.getMultiThreshold());
        this.setDiscount(hotelOfMultiCouponVO.getDiscount());
        this.setStatus(hotelOfMultiCouponVO.getStatus());
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", hotelId=" + hotelId +
                ", couponType=" + couponType.toString() +
                ", couponName='" + couponName + '\'' +
                ", targetMoney=" + targetMoney +
                ", discount=" + discount +
                ", discountMoney=" + discountMoney +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + status +
                '}';
    }
}
