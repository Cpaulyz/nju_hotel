package com.example.hotel.bl.coupon;

import com.example.hotel.HotelApplication;
import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.enums.CouponType;
import com.example.hotel.enums.OrderStateType;
import com.example.hotel.enums.RoomType;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HotelApplication.class)
@Transactional
public class CouponServiceImplTest {
    @Autowired
    CouponService couponService;

    @Test
    public void addHotelTargetMoneyCouponTest(){
        HotelTargetMoneyCouponVO hotelTargetMoneyCouponVO=new HotelTargetMoneyCouponVO();
        hotelTargetMoneyCouponVO.setType(CouponType.Target);
        hotelTargetMoneyCouponVO.setId(666);
        hotelTargetMoneyCouponVO.setName("测试满减优惠");
        hotelTargetMoneyCouponVO.setDescription("300-100");
        hotelTargetMoneyCouponVO.setTargetMoney(300.0);
        hotelTargetMoneyCouponVO.setDiscountMoney(100.0);
        hotelTargetMoneyCouponVO.setHotelId(1);
        hotelTargetMoneyCouponVO.setStatus(1);
        CouponVO couponVO=couponService.addHotelTargetMoneyCoupon(hotelTargetMoneyCouponVO);
        assert couponVO.getId()!=666;
        Coupon coupon=couponService.getHotelCouponById(couponVO.getId());
        assert coupon!=null;
        hotelTargetMoneyCouponVO.setStatus(0);
        couponVO=couponService.updateHotelTargetMoneyCoupon(hotelTargetMoneyCouponVO);
        assert couponVO.getStatus()!=1;
    }

    @Test
    public void addHotelTimeCouponTest(){
        HotelTimeCouponVO hotelTimeCouponVO=new HotelTimeCouponVO();
        hotelTimeCouponVO.setType(CouponType.Festival);
        hotelTimeCouponVO.setId(666);
        hotelTimeCouponVO.setName("测试节日优惠");
        hotelTimeCouponVO.setDescription("520打8折");
        hotelTimeCouponVO.setStartTime(LocalDateTime.parse("2021-05-20 00:00:00",DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        hotelTimeCouponVO.setEndTime(LocalDateTime.parse("2021-05-21 00:00:00",DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        hotelTimeCouponVO.setDiscount(0.8);
        hotelTimeCouponVO.setHotelId(0);    //网站优惠
        hotelTimeCouponVO.setStatus(1);
        CouponVO couponVO=couponService.addHotelTimeCoupon(hotelTimeCouponVO);
        assert couponVO.getId()!=666;
        Coupon coupon=couponService.getHotelCouponById(couponVO.getId());
        assert coupon!=null;
        hotelTimeCouponVO.setStatus(0);
        couponVO=couponService.updateHotelTimeCoupon(hotelTimeCouponVO);
        assert couponVO.getStatus()!=1;
    }

    @Test
    public void addHotelOfMultiCouponVOTest(){
        HotelOfMultiCouponVO hotelOfMultiCouponVO=new HotelOfMultiCouponVO();
        hotelOfMultiCouponVO.setType(CouponType.Multi);
        hotelOfMultiCouponVO.setId(666);
        hotelOfMultiCouponVO.setName("测试多间优惠");
        hotelOfMultiCouponVO.setDescription("3减打9折");
        hotelOfMultiCouponVO.setHotelId(1);
        hotelOfMultiCouponVO.setMultiThreshold(3);
        hotelOfMultiCouponVO.setDiscount(0.9);
        hotelOfMultiCouponVO.setStatus(1);
        CouponVO couponVO=couponService.addHotelOfMultiCoupon(hotelOfMultiCouponVO);
        assert couponVO.getId()!=666;
        Coupon coupon=couponService.getHotelCouponById(couponVO.getId());
        assert coupon!=null;
        hotelOfMultiCouponVO.setStatus(0);
        couponVO=couponService.updateHotelOfMultiCoupon(hotelOfMultiCouponVO);
        assert couponVO.getStatus()!=1;
    }

    @Test
    public void getCouponTest(){
        addHotelOfMultiCouponVOTest();
        addHotelTargetMoneyCouponTest();
        addHotelTimeCouponTest();
        List<Coupon> list=couponService.getHotelAllCoupon(1);
        assert list!=null;
        list=couponService.getWebAllCoupons();
        assert list.get(list.size()-1).getDescription().equals("520打8折");
    }

    @Test
    public void matchCouponTest(){
        addHotelOfMultiCouponVOTest();
        addHotelTargetMoneyCouponTest();
        addHotelTimeCouponTest();
        OrderVO orderVO=new OrderVO();
        orderVO.setId(1);
        orderVO.setUserId(1);
        orderVO.setHotelId(1);
        orderVO.setCheckInDate("2020-08-08");
        orderVO.setCheckOutDate("2020-08-09");
        orderVO.setRoomType("BigBed");
        orderVO.setRoomNum(3);
        orderVO.setHaveChild(false);
        orderVO.setCreateDate("2020-08-01");
        orderVO.setPrice(1000.0);
        orderVO.setOrderState(OrderStateType.Ordered);
        List<Coupon> list=couponService.getMatchOrderCoupon(orderVO);
        assert list.size()!=0;
    }

}
