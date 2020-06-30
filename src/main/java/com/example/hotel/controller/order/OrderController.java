package com.example.hotel.controller.order;

import com.example.hotel.bl.order.OrderService;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: chenyizong
 * @Date: 2020-02-29
 */


@RestController()
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/addOrder")
    public ResponseVO reserveHotel(@RequestBody OrderVO orderVO){
        return orderService.addOrder(orderVO);
    }

    @GetMapping("/getAllOrders")
    public ResponseVO retrieveAllOrders(){
        return ResponseVO.buildSuccess(orderService.getAllOrders());
    }

    @GetMapping("/{userId}/getUserOrders")
    public  ResponseVO retrieveUserOrders(@PathVariable int userId){
        return ResponseVO.buildSuccess(orderService.getUserOrders(userId));
    }

    @GetMapping("/{managerId}/getManagerOrders")
    public ResponseVO retrieveManagerOrders(@PathVariable int managerId){
        return ResponseVO.buildSuccess(orderService.getManagerOrders(managerId));
    }

    @GetMapping("/{orderId}/annulOrder")
    public ResponseVO annulOrder(@PathVariable int orderId){
        return orderService.annulOrder(orderId);
    }

    @GetMapping("/{hotelId}/allOrders")    //注意这个变到order里了,前面的url有变
    public ResponseVO retrieveHotelOrders(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(orderService.getHotelOrders(hotelId));
    }
    @GetMapping("{orderId}/getOrderDetail") // by cyz
    public ResponseVO getOrderDetail(@PathVariable int orderId){
        return ResponseVO.buildSuccess(orderService.getOrderDetail(orderId));
    }

    @GetMapping("{orderId}/executeOrder")
    public ResponseVO executeOrder(@PathVariable Integer orderId){
        return ResponseVO.buildSuccess(orderService.executeOrder(orderId));
    }





}
