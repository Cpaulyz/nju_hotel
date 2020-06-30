package com.example.hotel.enums;

public enum HotelService {
    WiFi("Wifi"),

    HotWater("热水"),

    WakeUp("叫醒服务");

    private String value;

    HotelService(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
