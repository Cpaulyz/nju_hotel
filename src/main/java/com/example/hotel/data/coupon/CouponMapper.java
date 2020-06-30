package com.example.hotel.data.coupon;

import com.example.hotel.po.Coupon;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CouponMapper {
    int insertCoupon(Coupon coupon);
    int updateCoupon(Coupon coupon);
    List<Coupon> selectByHotelId(Integer hotelId);
    List<Coupon> getWebAllCoupon();
    Coupon selectById(Integer id);
}
