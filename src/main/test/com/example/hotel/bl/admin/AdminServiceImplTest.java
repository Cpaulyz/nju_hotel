package com.example.hotel.bl.admin;

import com.example.hotel.HotelApplication;
import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.User;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HotelApplication.class)
@Transactional
public class AdminServiceImplTest {
    @Autowired
    AdminService adminService;

    @Test
    public void addManagerSuccessTest(){
        UserVO userVO  = new UserVO();
        userVO.setEmail("888@qq.com");
        userVO.setPassword("123456");
        userVO.setPhoneNumber("13159175930");
        userVO.setUserName("addUserTest");
        ResponseVO responseVO = adminService.addManager(userVO);
        assert responseVO.getSuccess();
        List<User> users = adminService.getAllUsers();
        for(User user:users){
            if(user.getEmail().equals(userVO.getEmail())){
                assert user.getPassword().equals(userVO.getPassword());
                assert user.getPhoneNumber().equals(userVO.getPhoneNumber());
                assert user.getUserName().equals(userVO.getUserName());
                assert user.getUserType() == UserType.Manager;
            }
        }
    }

    @Test
    public void addManagerErrorTest(){
        UserVO userVO  = new UserVO();
        userVO.setEmail("111@qq.com");
        userVO.setPassword("123456");
        userVO.setPhoneNumber("13159175930");
        userVO.setUserName("addUserTest");
        ResponseVO responseVO = adminService.addManager(userVO);
        assert !responseVO.getSuccess();
        System.out.println(responseVO.getMessage());
    }

    @Test
    public void addHotelManagerSuccessTest(){
        UserVO userVO  = new UserVO();
        userVO.setEmail("888@qq.com");
        userVO.setPassword("123456");
        userVO.setPhoneNumber("13159175930");
        userVO.setUserName("addUserTest");
        ResponseVO responseVO = adminService.addHotelManager(userVO);
        assert responseVO.getSuccess();
        List<User> users = adminService.getAllUsers();
        for(User user:users){
            if(user.getEmail().equals(userVO.getEmail())){
                assert user.getPassword().equals(userVO.getPassword());
                assert user.getPhoneNumber().equals(userVO.getPhoneNumber());
                assert user.getUserName().equals(userVO.getUserName());
                assert user.getUserType() == UserType.HotelManager;
            }
        }
    }

    @Test
    public void resetPasswordTest(){
        UserVO userVO  = new UserVO();
        userVO.setEmail("888@qq.com");
        userVO.setPassword("666666");
        userVO.setPhoneNumber("13159175930");
        userVO.setUserName("addUserTest");
        ResponseVO responseVO = adminService.addHotelManager(userVO);
        assert responseVO.getSuccess();
        adminService.resetPassword((Integer) responseVO.getContent());
        List<User> users = adminService.getAllUsers();
        for(User user:users){
            if(user.getEmail().equals(userVO.getEmail())){
                assert user.getPassword().equals("e10adc3949ba59abbe56e057f20f883e");
            }
        }
    }
}
