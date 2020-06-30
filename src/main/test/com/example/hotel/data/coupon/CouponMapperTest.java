package com.example.hotel.data.coupon;

import com.example.hotel.enums.CouponType;
import com.example.hotel.po.Coupon;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CouponMapperTest {
    @Autowired
    CouponMapper couponMapper;

    @Test
    public void insertCouponTest(){
        Coupon coupon = new Coupon();
        coupon.setDescription("300-100");
        coupon.setCouponType(CouponType.Target);
        coupon.setCouponName("测试满减优惠");
        coupon.setTargetMoney(300.0);
        coupon.setDiscountMoney(100.0);
        coupon.setHotelId(1);
        coupon.setStatus(1);
        couponMapper.insertCoupon(coupon);
        Coupon coupon1 = couponMapper.selectById(coupon.getId());
        System.out.println(coupon1);
        assert coupon.getDescription().equals(coupon1.getDescription());
        assert coupon.getCouponType().equals(coupon1.getCouponType());
        assert coupon.getCouponName().equals(coupon1.getCouponName());
        assert coupon.getTargetMoney() == coupon1.getTargetMoney();
        assert coupon.getDiscountMoney() == coupon1.getDiscountMoney();
        assert coupon.getHotelId().equals(coupon1.getHotelId());
        assert coupon.getStatus().equals(coupon1.getStatus());
    }

    @Test
    public void updateCouponTest(){
        Coupon coupon = new Coupon();
        coupon.setDescription("300-100");
        coupon.setCouponType(CouponType.Target);
        coupon.setCouponName("测试满减优惠");
        coupon.setTargetMoney(300.0);
        coupon.setDiscountMoney(100.0);
        coupon.setHotelId(1);
        coupon.setStatus(1);
        couponMapper.insertCoupon(coupon);
        coupon.setDescription("改了");
        coupon.setTargetMoney(400.0);
        coupon.setDiscountMoney(50.0);
        couponMapper.updateCoupon(coupon);
        Coupon coupon1 = couponMapper.selectById(coupon.getId());
        System.out.println(coupon1);
        assert coupon.getDescription().equals(coupon1.getDescription());
        assert coupon.getTargetMoney() == coupon1.getTargetMoney();
        assert coupon.getDiscountMoney() == coupon1.getDiscountMoney();
    }

    @Test
    public void selectByHotelIdTest(){
        assert couponMapper.selectByHotelId(1).size()>0;
    }
    @Test
    public void getWebAllCouponTest(){
        assert couponMapper.getWebAllCoupon().size()>0;
    }

}
