package com.example.hotel.data.admin;

import com.example.hotel.enums.UserType;
import com.example.hotel.po.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AdminMapperTest {

    @Autowired
    AdminMapper adminMapper;

    @Test
    public void addUserTest(){
        User userVO = new User();
        userVO.setEmail("888@qq.com");
        userVO.setPassword("123456");
        userVO.setPhoneNumber("13159175930");
        userVO.setUserName("addUserTest");
        userVO.setUserType(UserType.HotelManager);
        adminMapper.addUser(userVO);
        List<User> users = adminMapper.getAllUsers();
        for(User user:users){
            if(user.getEmail().equals(userVO.getEmail())){
                assert user.getPassword().equals(userVO.getPassword());
                assert user.getPhoneNumber().equals(userVO.getPhoneNumber());
                assert user.getUserName().equals(userVO.getUserName());
                assert user.getUserType() == userVO.getUserType();
            }
        }
    }
    @Test
    public void getAllManagersTest(){
        List<User> managers = adminMapper.getAllManagers();
        for (User user:managers){
            assert user.getUserType()==UserType.HotelManager;
        }
    }

    @Test
    public void getAllUsers(){
        List<User> users = adminMapper.getAllUsers();
        for (User user:users){
            assert user.getUserType()!=UserType.Admin;
        }
    }

    @Test
    public void resetPasswordTest(){
        User userVO = new User();
        userVO.setEmail("888@qq.com");
        userVO.setPassword("888888");
        userVO.setPhoneNumber("13159175930");
        userVO.setUserName("addUserTest");
        userVO.setUserType(UserType.HotelManager);
        adminMapper.addUser(userVO);
        adminMapper.resetPassword(userVO.getId());
        List<User> users = adminMapper.getAllUsers();
        for (User user:users){
            if (user.getId()==userVO.getId()){
                assert user.getPassword().equals("e10adc3949ba59abbe56e057f20f883e");
            }
        }
    }
}
