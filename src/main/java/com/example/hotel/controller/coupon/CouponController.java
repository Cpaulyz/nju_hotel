package com.example.hotel.controller.coupon;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping("/hotelTargetMoney")
    public ResponseVO addHotelTargetMoneyCoupon(@RequestBody HotelTargetMoneyCouponVO hotelTargetMoneyCouponVO) {
        CouponVO couponVO = couponService.addHotelTargetMoneyCoupon(hotelTargetMoneyCouponVO);
        return ResponseVO.buildSuccess(couponVO);
    }

    @PostMapping("/updateHotelTargetMoney")
    public ResponseVO updateHotelTargetMoneyCoupon(@RequestBody HotelTargetMoneyCouponVO hotelTargetMoneyCouponVO) {
        CouponVO couponVO = couponService.updateHotelTargetMoneyCoupon(hotelTargetMoneyCouponVO);
        return ResponseVO.buildSuccess(couponVO);
    }

    @PostMapping("/hotelTime")
    public ResponseVO addHotelTimeCoupon(@RequestBody HotelTimeCouponVO hotelTimeCouponVO){
        CouponVO couponVO=couponService.addHotelTimeCoupon(hotelTimeCouponVO);
        return ResponseVO.buildSuccess(couponVO);
    }

    @PostMapping("/updateHotelTime")
    public ResponseVO updateHotelTimeCoupon(@RequestBody HotelTimeCouponVO hotelTimeCouponVO){
        CouponVO couponVO=couponService.updateHotelTimeCoupon(hotelTimeCouponVO);
        return ResponseVO.buildSuccess(couponVO);
    }

    @PostMapping("/hotelOfMulti")
    public ResponseVO addHotelOfMultiCoupon(@RequestBody HotelOfMultiCouponVO hotelOfMultiCouponVO){
        CouponVO couponVO=couponService.addHotelOfMultiCoupon(hotelOfMultiCouponVO);
        return ResponseVO.buildSuccess(couponVO);
    }

    @PostMapping("/updateHotelOfMulti")
    public ResponseVO updateHotelOfMultiCoupon(@RequestBody HotelOfMultiCouponVO hotelOfMultiCouponVO){
        CouponVO couponVO=couponService.updateHotelOfMultiCoupon(hotelOfMultiCouponVO);
        return ResponseVO.buildSuccess(couponVO);
    }

    @GetMapping("/hotelAllCoupons")
    public ResponseVO getHotelAllCoupons(@RequestParam Integer hotelId) {
        return ResponseVO.buildSuccess(couponService.getHotelAllCoupon(hotelId));
    }

    @GetMapping("/webAllCoupons")
    public ResponseVO getWebAllCoupons(){
        return ResponseVO.buildSuccess(couponService.getWebAllCoupons());
    }

    @GetMapping("/orderMatchCoupons")
    public ResponseVO getOrderMatchCoupons(@RequestParam Integer userId,
                                           @RequestParam Integer hotelId,
                                           @RequestParam Double orderPrice,
                                           @RequestParam Integer roomNum,
                                           @RequestParam String checkIn,
                                           @RequestParam String checkOut) {
        OrderVO requestOrderVO = new OrderVO();
        requestOrderVO.setUserId(userId);
        requestOrderVO.setHotelId(hotelId);
        requestOrderVO.setPrice(orderPrice);
        requestOrderVO.setRoomNum(roomNum);
        requestOrderVO.setCheckInDate(checkIn);
        requestOrderVO.setCheckOutDate(checkOut);
        return ResponseVO.buildSuccess(couponService.getMatchOrderCoupon(requestOrderVO));
        //  这里回
    }

    @GetMapping("/getCouponById")
    public ResponseVO getCouponById(@RequestParam Integer id){
        Coupon coupon = couponService.getHotelCouponById(id);
        return ResponseVO.buildSuccess(coupon);
    }

}
