package com.example.hotel.data.user;

import com.alibaba.fastjson.JSON;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountMapperTest {
    @Autowired
    AccountMapper accountMapper;
    String NAME = "name";
    String PHONE = "13159175930";
    String PASSWORD = "123456";
    String EMAIL = "88888888@qq.com";

    @Test
    public void getAccountByIdTest(){
        User user = accountMapper.getAccountById(7);
        System.out.println(JSON.toJSON(user));
    }
    @Test
    public void getAccountByNameTest(){
        User user = accountMapper.getAccountByName("123@qq.com");
        System.out.println(JSON.toJSON(user));
    }

    @Test
    public void createNewAccountTest(){
        User user = new User();
        user.setUserType(UserType.Client);
        user.setPhoneNumber(PHONE);
        user.setUserName(NAME);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        user.setCredit(100);
        accountMapper.createNewAccount(user);
        User user1 = accountMapper.getAccountById(user.getId());
        assert user.getPassword().equals(user1.getPassword());
        assert user.getEmail().equals(user1.getEmail());
        assert user.getUserType().equals(user1.getUserType());
        assert user.getUserName().equals(user1.getUserName());
        assert user.getPhoneNumber().equals(user1.getPhoneNumber());
        assert user.getCredit() == user1.getCredit();
    }

    @Test
    public void updateAccountTest(){
        String newName = "newName";
        String newPhone = "newPhone";
        accountMapper.updateAccount(4,newName,newPhone);
        User user = accountMapper.getAccountById(4);
        assert user.getUserName().equals(newName);
        assert user.getPhoneNumber().equals(newPhone);
    }

    @Test
    public void updatePasswordTest(){
        String newPSW = "88889999";
        accountMapper.updatePassword(4,newPSW);
        User user = accountMapper.getAccountById(4);
        assert user.getPassword().equals(newPSW);
    }

    @Test
    public void updateCreditTest(){
        accountMapper.updateCredit(4,88);
        User user = accountMapper.getAccountById(4);
        assert user.getCredit()==88;
    }
}
