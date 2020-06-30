package com.example.hotel.bl.order;

import com.alibaba.fastjson.JSON;
import com.example.hotel.HotelApplication;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.enums.OrderStateType;
import com.example.hotel.po.Order;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HotelApplication.class)
@Transactional
public class OrderServiceImplTest {
    @Autowired
    OrderService orderService;

    @Test
    public void addOrderTest(){
        OrderVO orderVO=new OrderVO();
        orderVO.setClientName("王雷");
        orderVO.setPhoneNumber("13159175930");
        orderVO.setUserId(0);
        orderVO.setHotelId(0);
        orderVO.setHotelName("哈哈哈");
        orderVO.setCheckInDate("2020-08-08");
        orderVO.setCheckOutDate("2020-08-09");
        orderVO.setRoomType("BigBed");
        orderVO.setRoomNum(3);
        orderVO.setHaveChild(false);
        orderVO.setPrice(1000.0);
        ResponseVO responseVO = orderService.addOrder(orderVO);
        assert responseVO.getSuccess();
    }

    @Test
    public void getAllOrdersTest(){
        addOrderTest();
        List<Order> orders = orderService.getAllOrders();
        Order order = orders.get(orders.size()-1);
        System.out.println(JSON.toJSON(order));
    }

    @Test
    public void annulOrderTest(){
        addOrderTest();
        List<Order> orders = orderService.getAllOrders();
        Order order = orders.get(orders.size()-1);
        orderService.annulOrder(order.getId());
        orders = orderService.getAllOrders();
        order = orders.get(orders.size()-1);
        assert order.getOrderState()==OrderStateType.Annulled;
    }

    @Test
    public void executeOrderTest(){
        addOrderTest();
        List<Order> orders = orderService.getAllOrders();
        Order order = orders.get(orders.size()-1);
        orderService.executeOrder(order.getId());
        orders = orderService.getAllOrders();
        order = orders.get(orders.size()-1);
        assert order.getOrderState()==OrderStateType.Executed;
    }

    @Test
    public void getOrderDetailTest(){
        addOrderTest();
        List<Order> orders = orderService.getAllOrders();
        Order order = orders.get(orders.size()-1);
        Order order1 = orderService.getOrderDetail(order.getId());
        assert order.equals(order1);
    }
}
