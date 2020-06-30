package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.enums.CouponType;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.springframework.stereotype.Service;

@Service
public class MultiCouponStrategyImpl implements CouponMatchStrategy {

    /**
     * 判断某个订单是否满足某种满减金额优惠策略
     * @param orderVO
     * @param coupon
     * @return
     */
    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        if(coupon.getCouponType()!= CouponType.Multi)
            return false;
        if(coupon.getStatus()==1)
            if(orderVO.getRoomNum()>=coupon.getMultiThreshold())
                return true;

        return false;
    }
}
