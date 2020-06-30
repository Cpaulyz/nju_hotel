package com.example.hotel.bl.user;

import com.example.hotel.HotelApplication;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.enums.ActionType;
import com.example.hotel.enums.SignType;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.User;
import com.example.hotel.vo.*;
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
public class AccountServiceImplTest {
    @Autowired
    AccountService accountService;
    String NAME = "name";
    String PHONE = "13159175930";
    String PASSWORD = "123456";
    String EMAIL = "88888888@qq.com";

    // 注册一个新用户，为了后面好复用
    public int createNewClient(){
        UserVO userVO = new UserVO();
        userVO.setUserName(NAME);
        userVO.setPhoneNumber(PHONE);
        userVO.setEmail(EMAIL);
        userVO.setCredit(100);
        userVO.setPassword(PASSWORD);
        ResponseVO responseVO = accountService.registerAccount(userVO);
        assert responseVO.getSuccess();
        UserForm userForm = new UserForm(); // 模拟前端传来的表单
        userForm.setEmail(userVO.getEmail());
        userForm.setPassword(userVO.getPassword());
        User user = accountService.login(userForm);
        return user.getId();
    }
    @Test
    /**
     * 测试注册失败
     */
    public void registerAccountErrorTest(){
        UserVO userVO = new UserVO();
        userVO.setUserName("name");
        userVO.setPhoneNumber("13159175930");
        userVO.setEmail("123@qq.com");
        userVO.setCredit(100);
        userVO.setPassword("123456");
        ResponseVO responseVO = accountService.registerAccount(userVO);
        assert !responseVO.getSuccess();
    }

    @Test
    /**
     * 该方法测试registerAccount、login、getUserInfo
     */
    public void registerAccountSuccessTest(){
        UserVO userVO = new UserVO();
        userVO.setUserName("name");
        userVO.setPhoneNumber("13159175930");
        userVO.setEmail("88888888@qq.com");
        userVO.setCredit(100);
        userVO.setPassword("123456");
        userVO.setUserType(UserType.Client);
        ResponseVO responseVO = accountService.registerAccount(userVO);
        assert responseVO.getSuccess(); // 注册成功
        UserForm userForm = new UserForm(); // 模拟前端传来的表单
        userForm.setEmail(userVO.getEmail());
        userForm.setPassword(userVO.getPassword());
        User user = accountService.login(userForm);
        assert user!=null;
        User userInfo = accountService.getUserInfo(user.getId());
        assert userInfo.getUserType()== UserType.Client;
        assert userInfo.getUserName().equals(userVO.getUserName());
        assert userInfo.getPhoneNumber().equals(userVO.getPhoneNumber());
        assert userInfo.getEmail().equals(userVO.getEmail());
        assert userInfo.getPassword().equals(userVO.getPassword());
        assert userInfo.getCredit()==userVO.getCredit();
    }

    @Test
    /**
     * 测试登录失败，模拟密码错误
     */
    public void loginErrorTest(){
        UserForm userForm = new UserForm();
        userForm.setEmail("123@qq.com");
        userForm.setPassword("888888");
        User user = accountService.login(userForm);
        assert user==null;
    }

    @Test
    /**
     * 测试修改用户信息
     */
    public void updateUserInfoTest(){
        // 先模拟注册一个新账户
        int userId = createNewClient();
        String newName = "newName";
        String newPhone = "13605058699";
        ResponseVO responseVO1  = accountService.updateUserInfo(userId,newName,newPhone);
        assert responseVO1.getSuccess();
        // 再getUserInfo检查一遍
        User userInfo = accountService.getUserInfo(userId);
        assert userInfo.getUserName().equals(newName);
        assert userInfo.getPhoneNumber().equals(newPhone);
    }

    @Test
    /**
     * 测试修改密码成功
     */
    public void updatePasswordSuccessTest(){
        int userId = createNewClient();
        PasswordForm passwordForm = new PasswordForm();
        passwordForm.setId(userId);
        passwordForm.setOldPassword(PASSWORD);
        String newPSW = "987654321";
        passwordForm.setNewPassword(newPSW);
        ResponseVO responseVO = accountService.updatePassword(passwordForm);
        assert  responseVO.getSuccess();
        // 再getUserInfo检查一遍
        User userInfo = accountService.getUserInfo(userId);
        assert userInfo.getPassword().equals(newPSW);
    }

    @Test
    /**
     * 测试修改密码失败
     */
    public void updatePasswordErrorTest(){
        int userId = createNewClient();
        PasswordForm passwordForm = new PasswordForm();
        passwordForm.setId(userId);
        passwordForm.setOldPassword(PASSWORD+"1"); // 随便给个错误密码
        String newPSW = "987654321";
        passwordForm.setNewPassword(newPSW);
        ResponseVO responseVO = accountService.updatePassword(passwordForm);
        assert  !responseVO.getSuccess();
        // 再getUserInfo检查一遍
        User userInfo = accountService.getUserInfo(userId);
        assert userInfo.getPassword().equals(PASSWORD); // 检查密码没有改变
    }

    @Test
    /**
     * 包括信用的一系列操作一起测试，如更新、查询
     */
    public void creditTest(){
        int userId = createNewClient();
        CreditVO creditVO = new CreditVO();
        creditVO.setUserId(userId);
        creditVO.setAction(ActionType.EXECUTE);
        creditVO.setChangeNum(50.0);
        creditVO.setResult(150.0);
        creditVO.setSign(SignType.ADD);
        creditVO.setTime("2020-06-19 00:00:00");
        // 加信用
        ResponseVO responseVO = accountService.updateCredit(creditVO);
        assert responseVO.getSuccess();
        creditVO.setId(null);
        creditVO.setResult(130.0);
        creditVO.setChangeNum(20.0);
        creditVO.setSign(SignType.SUB);
        creditVO.setAction(ActionType.EXCEPTION);
        // 异常
        responseVO = accountService.updateCredit(creditVO);
        assert responseVO.getSuccess();
        creditVO.setId(null);
        creditVO.setAction(ActionType.ANNUL);
        creditVO.setChangeNum(30.0);
        creditVO.setResult(100.0);
        // 取消
        responseVO = accountService.updateCredit(creditVO);
        assert responseVO.getSuccess();
        creditVO.setId(null);
        creditVO.setAction(ActionType.TOPUP);
        creditVO.setChangeNum(50.0);
        creditVO.setResult(150.0);
        creditVO.setSign(SignType.ADD);
        // 重置
        responseVO = accountService.updateCredit(creditVO);
        assert responseVO.getSuccess();
        List<CreditVO> creditVOS = (List<CreditVO>) accountService.checkAllCredit(userId).getContent();
        System.out.println(creditVOS);
    }
}
