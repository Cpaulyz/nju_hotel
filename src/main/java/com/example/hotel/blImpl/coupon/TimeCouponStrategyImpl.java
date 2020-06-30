package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.enums.CouponType;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;


@Service
public class TimeCouponStrategyImpl implements CouponMatchStrategy {
    /**
     * 判断某个订单是否满足某种限时优惠策略
     * @param orderVO
     * @return
     */
    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        //不管这个订单的createTime是什么时候 只要他的checkInDate是这个节日就可以用【即提前预定的情况】
        //nt助教非要一个用"yyyy-DD-mm"的string 另一个用精确到分秒的localDataTime 这个类还不能直接转成只精确到日期的string 这代码写的真nm离谱
        if(coupon.getCouponType()!= CouponType.Festival) // 得加上这个判断，不然下面format到null会报错   by cyz
            return false;

        if(coupon.getStatus()==1) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String startTime = df.format(coupon.getStartTime()).substring(0,10);
            String endTime = df.format(coupon.getEndTime()).substring(0,10);
            String checkInDate=orderVO.getCheckInDate();
            String checkOutDate=orderVO.getCheckOutDate();


            if(startTime.compareTo(checkInDate)<=0 && endTime.compareTo(checkOutDate)>=0)
                return true;
        }
        return false;
    }
}