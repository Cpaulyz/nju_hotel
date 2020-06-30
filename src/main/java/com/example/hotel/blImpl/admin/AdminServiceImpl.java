package com.example.hotel.blImpl.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.data.admin.AdminMapper;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Service
public class AdminServiceImpl implements AdminService {
    private final static String ACCOUNT_EXIST = "账号已存在,请使用其他邮箱";
    private final static String RESET_ERROR = "密码重置失败";
    @Autowired
    AdminMapper adminMapper;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    HotelMapper hotelMapper;

    @Override
    public ResponseVO addManager(UserVO userVO) {
        return addUser(userVO,UserType.Manager);
    }

    @Override
    public ResponseVO addHotelManager(UserVO userVO) {
        return addUser(userVO,UserType.HotelManager);
    }

    @Override
    public List<User> getAllManagers() {
        return adminMapper.getAllManagers();
    }

    @Override
    public List<User> getAllUsers() {
        return adminMapper.getAllUsers();
    }

    @Override
    public ResponseVO resetPassword(int userId) {
        try{
            adminMapper.resetPassword(userId);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(RESET_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    /**
     * 删除账户
     * 1. 酒店管理人员账户，连同酒店一起删除
     * 2. 管理员账户，不允许删除
     * 3. 其他账户，直接删除
     * @param userId
     * @return
     */
    @Override
    public ResponseVO deleteUser(int userId) {
        User user = accountMapper.getAccountById(userId);
        if(user.getUserType().equals(UserType.Admin)){
            return ResponseVO.buildFailure("无法删除管理员账户");
        }else if(user.getUserType().equals(UserType.HotelManager)){
            hotelMapper.deleteHotelByHotelManagerId(userId);
            accountMapper.deleteUserById(userId);
        }else {
            accountMapper.deleteUserById(userId);
        }
        return ResponseVO.buildSuccess();
    }


    /*
     添加账号，添加成功的话返回值的ResponseVO是新账号的ID值。
     检测邮箱是否已存在，如果存在的话返回错误信息ACCOUNT_EXIST
     */
    public ResponseVO addUser(UserVO userVO,UserType userType){
        User user = new User();
        user.setEmail(userVO.getEmail());
        user.setPassword(userVO.getPassword());
        user.setUserName(userVO.getUserName());
        user.setPhoneNumber(userVO.getPhoneNumber());
        user.setUserType(userType);
        try {
            adminMapper.addUser(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess(user.getId());
    }
}
