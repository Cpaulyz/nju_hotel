package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.data.coupon.CouponMapper;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CouponServiceImpl implements CouponService {


    private final TargetMoneyCouponStrategyImpl targetMoneyCouponStrategy;
    private final TimeCouponStrategyImpl timeCouponStrategy;
    private final MultiCouponStrategyImpl multiCouponStrategy;
    private final CouponMapper couponMapper;

    private static List<CouponMatchStrategy> strategyList = new ArrayList<>();

    @Autowired
    public CouponServiceImpl(TargetMoneyCouponStrategyImpl targetMoneyCouponStrategy,
                             TimeCouponStrategyImpl timeCouponStrategy,
                             MultiCouponStrategyImpl multiCouponStrategy,
                             CouponMapper couponMapper) {
        this.couponMapper = couponMapper;
        this.targetMoneyCouponStrategy = targetMoneyCouponStrategy;
        this.timeCouponStrategy = timeCouponStrategy;
        this.multiCouponStrategy=multiCouponStrategy;
        strategyList.add(targetMoneyCouponStrategy);
        strategyList.add(timeCouponStrategy);
    }


    @Override
    public List<Coupon> getMatchOrderCoupon(OrderVO orderVO) {

        List<Coupon> hotelCoupons = getHotelAllCoupon(orderVO.getHotelId());
        List<Coupon> availAbleCoupons = new ArrayList<>();

        for (int i = 0; i < hotelCoupons.size(); i++) {
            for (CouponMatchStrategy strategy : strategyList) {
                if (strategy.isMatch(orderVO, hotelCoupons.get(i))) {
                    availAbleCoupons.add(hotelCoupons.get(i));
                }
            }
        }
        List<Coupon> webCoupons=getWebAllCoupons();    //所有网站优惠
        if(webCoupons!=null)
            availAbleCoupons.addAll(webCoupons);

        return availAbleCoupons;
    }

    @Override
    public List<Coupon> getHotelAllCoupon(Integer hotelId) {
        List<Coupon> hotelCoupons = couponMapper.selectByHotelId(hotelId);
        return hotelCoupons;
    }

    @Override
    public List<Coupon> getWebAllCoupons() {
        List<Coupon> webCoupons=couponMapper.getWebAllCoupon();
        return webCoupons;
    }


    @Override
    public Coupon getHotelCouponById(Integer couponId) {      //根据couponId获取此酒店的某个特定优惠
        Coupon coupon = couponMapper.selectById(couponId);
        return coupon;
    }

    //下面都是增加和修改各类优惠券
    @Override
    public CouponVO addHotelTargetMoneyCoupon(HotelTargetMoneyCouponVO hotelTargetMoneyCouponVO) {
        Coupon coupon = new Coupon(hotelTargetMoneyCouponVO);
        couponMapper.insertCoupon(coupon);
        hotelTargetMoneyCouponVO.setId(coupon.getId());  // 突然发现这个insert的返回值也只是影响了多少行 id已经自动赋值了
        return hotelTargetMoneyCouponVO;
    }

    @Override
    public CouponVO updateHotelTargetMoneyCoupon(HotelTargetMoneyCouponVO hotelTargetMoneyCouponVO) {
        Coupon coupon = new Coupon(hotelTargetMoneyCouponVO);
        couponMapper.updateCoupon(coupon);    //update返回值只是影响多少行 没啥用
        return hotelTargetMoneyCouponVO;
    }

    @Override
    public CouponVO addHotelTimeCoupon(HotelTimeCouponVO hotelTimeCouponVO) {
        Coupon coupon = new Coupon(hotelTimeCouponVO);
        couponMapper.insertCoupon(coupon);
        hotelTimeCouponVO.setId(coupon.getId());
        return hotelTimeCouponVO;
    }

    @Override
    public CouponVO updateHotelTimeCoupon(HotelTimeCouponVO hotelTimeCouponVO) {
        Coupon coupon = new Coupon(hotelTimeCouponVO);
        couponMapper.updateCoupon(coupon);    //update返回值只是影响多少行 没啥用
        return hotelTimeCouponVO;
    }

    @Override
    public CouponVO addHotelOfMultiCoupon(HotelOfMultiCouponVO hotelOfMultiCouponVO) {
        Coupon coupon = new Coupon(hotelOfMultiCouponVO);
        couponMapper.insertCoupon(coupon);
        hotelOfMultiCouponVO.setId(coupon.getId());
        return hotelOfMultiCouponVO;
    }

    @Override
    public CouponVO updateHotelOfMultiCoupon(HotelOfMultiCouponVO hotelOfMultiCouponVO) {
        Coupon coupon = new Coupon(hotelOfMultiCouponVO);
        couponMapper.updateCoupon(coupon);    //update返回值只是影响多少行 没啥用
        return hotelOfMultiCouponVO;
    }

}
