package com.example.hotel.data.order;

import com.example.hotel.enums.OrderStateType;
import com.example.hotel.enums.RoomType;
import com.example.hotel.po.Order;
import com.example.hotel.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Mapper
@Repository
public interface OrderMapper {

    int addOrder(Order order);

    List<Order> getAllOrders();

    List<Order> getUserOrders(@Param("userId") int userId);

    List<Order> getHotelAllOrders(@Param("hotelId") int hotelId);

    int updateOrderState(@Param("orderId") int orderId, @Param("state")OrderStateType stateType);

    Order getOrderById(@Param("orderId") int orderId);

    void checkException();

    List<Order> getManagerOrders(int managerId);

    Integer getOrderNumByDate(@Param("hotelId") int hotelId, @Param("roomType") RoomType roomType, @Param("date") String date);
}
