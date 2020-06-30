package com.example.hotel.po;

import com.example.hotel.enums.OrderStateType;

import java.util.Date;
import java.util.Objects;

/**
 * @Author: chenyizong
 * @Date: 2020-02-29
 */
public class Order {
    private Integer id;
    private Integer userId;
    private Integer hotelId;
    private String hotelName;
    private String checkInDate;
    private String checkOutDate;
    private String roomType;
    private Integer roomNum;
    private Integer peopleNum;
    private boolean haveChild;
    private String createDate;
    private Double price;
    private String clientName;
    private String phoneNumber;
    private OrderStateType orderState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public boolean isHaveChild() {
        return haveChild;
    }

    public void setHaveChild(boolean haveChild) {
        this.haveChild = haveChild;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public OrderStateType getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderStateType orderState) {
        this.orderState = orderState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return haveChild == order.haveChild &&
                Objects.equals(id, order.id) &&
                Objects.equals(userId, order.userId) &&
                Objects.equals(hotelId, order.hotelId) &&
                Objects.equals(hotelName, order.hotelName) &&
                Objects.equals(checkInDate, order.checkInDate) &&
                Objects.equals(checkOutDate, order.checkOutDate) &&
                Objects.equals(roomType, order.roomType) &&
                Objects.equals(roomNum, order.roomNum) &&
                Objects.equals(peopleNum, order.peopleNum) &&
                Objects.equals(createDate, order.createDate) &&
                Objects.equals(price, order.price) &&
                Objects.equals(clientName, order.clientName) &&
                Objects.equals(phoneNumber, order.phoneNumber) &&
                orderState == order.orderState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, hotelId, hotelName, checkInDate, checkOutDate, roomType, roomNum, peopleNum, haveChild, createDate, price, clientName, phoneNumber, orderState);
    }
}
