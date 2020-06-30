package com.example.hotel.bl.order;

import com.example.hotel.po.Order;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
public interface OrderService {

    /**
     * 预订酒店
     * @param orderVO
     * @return
     */
    ResponseVO addOrder(OrderVO orderVO);

    /**
     * 获得所有订单信息
     * @return
     */
    List<Order> getAllOrders();

    /**
     * 获得指定用户的所有订单信息
     * @param userId
     * @return
     */
    List<Order> getUserOrders(int userId);

    /**
     * 获得指定酒店管理人员的所有订单信息
     * @param managerId
     * @return
     */
    List<Order> getManagerOrders(int managerId);
    /**
     * 撤销订单
     * @param orderId
     * @return
     */
    ResponseVO annulOrder(int orderId);

    /**
     * 查看酒店的所有订单
     * @param hotelId
     * @return
     */
    List<Order> getHotelOrders(Integer hotelId);

    /**
     * 给orderId，返回order的所有信息
     * @param orderId
     * @return
     */
    Order getOrderDetail(int orderId);

    /**
     * 执行订单
     * @param orderId
     * @return
     * */
    ResponseVO executeOrder(int orderId);

    /**
     * 系统自动检查有无异常订单
     * */
    void checkException();
}
