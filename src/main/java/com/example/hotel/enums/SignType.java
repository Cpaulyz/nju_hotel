package com.example.hotel.enums;

public enum SignType {

    ADD("加"),
    SUB("减");
    private String value;

    SignType(String value){
        this.value=value;
    }

    @Override
    public String toString(){
        return value;
    }
}
