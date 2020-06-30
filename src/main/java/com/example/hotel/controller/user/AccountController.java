package com.example.hotel.controller.user;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.po.User;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController()
@RequestMapping("/api/user")
public class AccountController {
    private final static String ACCOUNT_INFO_ERROR = "用户名或密码错误";
    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public ResponseVO login(@RequestBody UserForm userForm) {

        System.out.println(userForm);
        User user = accountService.login(userForm);
        if (user == null) {
            return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
        }
        return ResponseVO.buildSuccess(user);
    }

    @PostMapping("/register")
    public ResponseVO registerAccount(@RequestBody UserVO userVO) {
        return accountService.registerAccount(userVO);
    }


    @GetMapping("/{id}/getUserInfo")
    public ResponseVO getUserInfo(@PathVariable int id) {
        User user = accountService.getUserInfo(id);
        if(user==null){
            return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
        }
        return ResponseVO.buildSuccess(user);
    }

    @PostMapping("/{id}/userInfo/update")
    public ResponseVO updateInfo(@RequestBody UserInfoVO userInfoVO,@PathVariable int id){
        return accountService.updateUserInfo(id,userInfoVO.getUserName(),userInfoVO.getPhoneNumber());
    }

    @PostMapping("/updateCredit")
    public ResponseVO updateCredit(@RequestBody CreditVO creditVO){
        return accountService.updateCredit(creditVO);
    }

    @GetMapping("/{id}/checkAllCredit")
    public ResponseVO checkAllCredit(@PathVariable int id){
        return accountService.checkAllCredit(id);
    }

    @PostMapping("/updatePassword")
    public ResponseVO updatePassword(@RequestBody PasswordForm passwordForm){
        return accountService.updatePassword(passwordForm);
    }
    @PostMapping("/updateAvatar")
    public ResponseVO updateAvatar(@RequestBody AvatarVO avatarVO){
        return accountService.updateAvatar(avatarVO);
    }

}
