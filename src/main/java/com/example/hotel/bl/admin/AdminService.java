package com.example.hotel.bl.admin;

import com.example.hotel.po.Hotel;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.UserVO;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
public interface AdminService {

    /**
     * 添加网站工作人员账号，注意不是酒店！
     * @param userVO
     * @return
     */
    ResponseVO addManager(UserVO userVO);

    /**
     * 添加酒店工作人员账号
     * @param userVO
     * @return ResponseVO里面包着新注册的ID
     */
    ResponseVO addHotelManager(UserVO userVO);

    /**
     * 获得所有酒店管理人员信息，暂时没用了【by cyz
     * @return
     */
    List<User> getAllManagers();

    /**
     * 获取所有用户的信息 by cyz
     * @return
     */
    List<User> getAllUsers();

    ResponseVO resetPassword(int userId);

    /**
     * 删除账户
     * 1. 酒店管理人员账户，连同酒店一起删除
     * 2. 管理员账户，不允许删除
     * 3. 其他账户，直接删除
     * @param userId
     * @return
     */
    ResponseVO deleteUser(int userId);
}
