package com.example.hotel.data.order;

import com.example.hotel.enums.OrderStateType;
import com.example.hotel.po.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderMapperTest {
    @Autowired
    OrderMapper orderMapper;

    @Test
    public void addOrderTest(){
        Order order=new Order();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String curdate = sf.format(date);
        order.setClientName("王雷");
        order.setPhoneNumber("13159175930");
        order.setUserId(0);
        order.setHotelId(0);
        order.setHotelName("哈哈哈");
        order.setCheckInDate("2020-08-08");
        order.setCheckOutDate("2020-08-09");
        order.setRoomType("BigBed");
        order.setRoomNum(3);
        order.setHaveChild(false);
        order.setPrice(1000.0);
        order.setCreateDate(curdate);
        order.setOrderState(OrderStateType.Ordered);
        orderMapper.addOrder(order);
        assert order.getId()!=null;
    }

    @Test
    public void getOrderByIdTest(){
        Order order=new Order();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String curdate = sf.format(date);
        order.setClientName("王雷");
        order.setPhoneNumber("13159175930");
        order.setUserId(0);
        order.setHotelId(0);
        order.setHotelName("哈哈哈");
        order.setCheckInDate("2020-08-08");
        order.setCheckOutDate("2020-08-09");
        order.setRoomType("BigBed");
        order.setRoomNum(3);
        order.setHaveChild(false);
        order.setPrice(1000.0);
        order.setCreateDate(curdate);
        order.setOrderState(OrderStateType.Ordered);
        orderMapper.addOrder(order);
        Order order1 = orderMapper.getOrderById(order.getId());
        System.out.println(order1);
    }
    @Test
    public void updateOrderStateTest(){
        Order order=new Order();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String curdate = sf.format(date);
        order.setClientName("王雷");
        order.setPhoneNumber("13159175930");
        order.setUserId(0);
        order.setHotelId(0);
        order.setHotelName("哈哈哈");
        order.setCheckInDate("2020-08-08");
        order.setCheckOutDate("2020-08-09");
        order.setRoomType("BigBed");
        order.setRoomNum(3);
        order.setHaveChild(false);
        order.setPrice(1000.0);
        order.setCreateDate(curdate);
        order.setOrderState(OrderStateType.Ordered);
        orderMapper.addOrder(order);
        orderMapper.updateOrderState(order.getId(),OrderStateType.Annulled);
        Order order1 = orderMapper.getOrderById(order.getId());
        assert order1.getOrderState().equals(OrderStateType.Annulled);
    }

}
