package com.example.hotel.vo;


import com.example.hotel.enums.RoomType;
import com.example.hotel.po.HotelRoom;

public class RoomVO {
    private Integer id;
    private RoomType roomType;
    private Double price;
    private Integer curNum;
    private Integer total;

    public RoomVO(){}

    public RoomVO(HotelRoom hotelRoom){
        this.id=hotelRoom.getId();
        this.roomType=hotelRoom.getRoomType();
        this.price=hotelRoom.getPrice();
        this.total=hotelRoom.getTotal();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCurNum() {
        return curNum;
    }

    public void setCurNum(Integer curNum) {
        this.curNum = curNum;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
