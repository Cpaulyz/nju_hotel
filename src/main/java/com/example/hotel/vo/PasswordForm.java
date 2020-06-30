package com.example.hotel.vo;

/**
 * 修改密码时传的RequestBody
 * 用对象装，比较安全
 * */
public class PasswordForm {
    // userId
    private int id;
    // 旧密码，用于检验
    private String oldPassword;
    // 新密码
    private String newPassword;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
