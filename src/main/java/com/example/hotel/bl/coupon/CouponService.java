package com.example.hotel.bl.coupon;

import com.example.hotel.po.Coupon;
import com.example.hotel.vo.*;

import java.util.List;

public interface CouponService {
    /**
     * 返回某一订单可用的优惠策略列表
     * @param orderVO
     * @return
     */
    List<Coupon> getMatchOrderCoupon(OrderVO orderVO);

    /**
     * 查看某个酒店提供的所有优惠策略（包括失效的）
     * @param hotelId
     * @return
     */
    List<Coupon> getHotelAllCoupon(Integer hotelId);

    /**
     * 获取网站优惠策略（包括失效的）
     * @return
     */
    List<Coupon> getWebAllCoupons();
    /**
     * 添加酒店满减优惠策略
     * @param hotelTargetMoneyCouponVO
     * @return
     */
    CouponVO addHotelTargetMoneyCoupon(HotelTargetMoneyCouponVO hotelTargetMoneyCouponVO);

    /**
     * 修改酒店满减优惠策略
     * @param hotelTargetMoneyCouponVO
     * @return
     */
    CouponVO updateHotelTargetMoneyCoupon(HotelTargetMoneyCouponVO hotelTargetMoneyCouponVO);

    /**
     * 添加酒店节日优惠策略
     * @param hotelTimeCouponVO
     * @return
     * */
    CouponVO addHotelTimeCoupon(HotelTimeCouponVO hotelTimeCouponVO);

    /**
     * 修改酒店节日优惠策略
     * @param hotelTimeCouponVO
     * @return
     * */
    CouponVO updateHotelTimeCoupon(HotelTimeCouponVO hotelTimeCouponVO);

    /**
     * 添加酒店多间优惠策略
     * @param hotelOfMultiCouponVO
     * @return
     * */
    CouponVO addHotelOfMultiCoupon(HotelOfMultiCouponVO hotelOfMultiCouponVO);

    /**
     * 修改酒店多间优惠策略
     * @param hotelOfMultiCouponVO
     * @return
     * */
    CouponVO updateHotelOfMultiCoupon(HotelOfMultiCouponVO hotelOfMultiCouponVO);

    /**
     * 获取优惠策略byId
     * @param couponId
     * @return 因为xml中resultMap是Coupon类型的 所以返回值是Coupon 而不是CouponVO
     */
    Coupon getHotelCouponById(Integer couponId);      //这个是获取这个酒店的某个特定优惠 不一定非要是满减的

}
