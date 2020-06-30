package com.example.hotel.blImpl.order;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.enums.ActionType;
import com.example.hotel.enums.OrderStateType;
import com.example.hotel.enums.SignType;
import com.example.hotel.po.Order;
import com.example.hotel.vo.CreditVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final static String RESERVE_ERROR = "预订失败";
    private final static String ROOMNUM_LACK = "预订房间数量剩余不足";
    private final static String ORDER_EXECUTE_ERROR = "订单不存在或订单已被撤销";
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    HotelService hotelService;
    @Autowired
    AccountService accountService;

    @Override
    public ResponseVO addOrder(OrderVO orderVO) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            String curdate = sf.format(date);
            orderVO.setCreateDate(curdate);
            orderVO.setOrderState(OrderStateType.Ordered);
            Order order = new Order();
            BeanUtils.copyProperties(orderVO,order);
            orderMapper.addOrder(order);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(RESERVE_ERROR);
        }
       return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderMapper.getAllOrders();
    }

    @Override
    public List<Order> getUserOrders(int userId) {
        return orderMapper.getUserOrders(userId);
    }

    @Override
    public List<Order> getManagerOrders(int managerId) {
        return orderMapper.getManagerOrders(managerId);
    }

    @Override
    public ResponseVO annulOrder(int orderId) {
        orderMapper.updateOrderState(orderId,OrderStateType.Annulled);
        return ResponseVO.buildSuccess(true);
    }
    @Override
    public List<Order> getHotelOrders(Integer hotelId) {
        List<Order> orders = getAllOrders();
        return orders.stream().filter(order -> order.getHotelId().equals(hotelId)).collect(Collectors.toList());
    }

    @Override
    public Order getOrderDetail(int orderId) {
        return orderMapper.getOrderById(orderId);
    }

    @Override
    public ResponseVO executeOrder(int orderId) {
        Order order;
        order=orderMapper.getOrderById(orderId);
        if(order==null || order.getOrderState()==OrderStateType.Annulled)
            return ResponseVO.buildFailure(ORDER_EXECUTE_ERROR);
        orderMapper.updateOrderState(orderId,OrderStateType.Executed);    //前端记得每次执行订单都要再调一下信用值更新的接口
        return ResponseVO.buildSuccess(true);
    }

    @Override
    @Scheduled(cron = "0 0 0 1/1 * ?")   // 每天零点检查一次
    public void checkException() {   // 下单了但没执行的变成异常状态 也就是寻找已下单的订单 且距离checkInDate已经一天
        try {
            List<Order> orderList=orderMapper.getAllOrders();
            if(orderList!=null){
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String currentDate = sdf.format(date);
                for(Order order:orderList){
                    if(order.getCheckInDate().compareTo(currentDate)<0 && order.getOrderState()==OrderStateType.Ordered){
                        orderMapper.updateOrderState(order.getId(),OrderStateType.Exception);
                        CreditVO creditVO=new CreditVO();
                        creditVO.setUserId(order.getUserId());
                        creditVO.setAction(ActionType.EXCEPTION);
                        creditVO.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                        creditVO.setOrderId(order.getId());
                        creditVO.setSign(SignType.SUB);
                        creditVO.setChangeNum(order.getPrice());
                        creditVO.setResult(accountMapper.getAccountById(order.getUserId()).getCredit() - order.getPrice());
                        accountService.updateCredit(creditVO);
                    }
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Scheduled(cron = "0 0 0 1/1 * ?")     // 每天零点检查一次
    public void changeException(){   // 异常超过一天的变成撤销 也就是寻找异常的订单 且距离checkInDate已经两天
        try {
            List<Order> orderList=orderMapper.getAllOrders();
            if(orderList!=null){
                LocalDate localDate=LocalDate.now();
                String lastDate=localDate.minusDays(2).toString();
                for(Order order:orderList){
                    if(order.getCheckInDate().compareTo(lastDate)<=0 && order.getOrderState()==OrderStateType.Exception)
                        orderMapper.updateOrderState(order.getId(),OrderStateType.Annulled);   // 异常的时候已经扣分了 这里就不扣了
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
