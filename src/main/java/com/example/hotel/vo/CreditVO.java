package com.example.hotel.vo;

import com.example.hotel.enums.ActionType;
import com.example.hotel.enums.SignType;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreditVO {

    private Integer id;

    private Integer userId;

    private ActionType action;    //由于什么原因引起的信用值变化

    private String time;

    private Integer orderId;

    private SignType sign;    //加还是减

    private Double changeNum;   //改变的数量

    private Double result;   //最终信用值是多少


    public CreditVO(){}

    public CreditVO(int userId){    //用户注册时初始化记录
        this.userId=userId;
        this.action=ActionType.INIT;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.time=df.format(new Date());  // 获取当前系统时间
        this.result=100.0;
    }

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

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
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

    @Override
    public String toString() {
        return "CreditVO{" +
                "id=" + id +
                ", userId=" + userId +
                ", action=" + action +
                ", time='" + time + '\'' +
                ", orderId=" + orderId +
                ", sign=" + sign +
                ", changeNum=" + changeNum +
                ", result=" + result +
                '}';
    }
}
