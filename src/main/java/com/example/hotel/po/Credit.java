package com.example.hotel.po;

import com.example.hotel.enums.ActionType;
import com.example.hotel.enums.SignType;

public class Credit {

    private Integer id;

    private Integer userId;

    private ActionType actionType;    //由于什么原因引起的信用值变化

    private String time;

    private Integer orderId;

    private SignType sign;    //加还是减

    private Double changeNum;   //改变的数量

    private Double result;   //最终信用值是多少



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

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public SignType getSign() {
        return sign;
    }

    public void setSign(SignType sign) {
        this.sign = sign;
    }

    public Double getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(Double changeNum) {
        this.changeNum = changeNum;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }
}
