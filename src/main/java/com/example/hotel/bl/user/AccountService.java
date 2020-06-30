package com.example.hotel.bl.user;

import com.example.hotel.po.User;
import com.example.hotel.vo.*;

/**
 * @author huwen
 * @date 2019/3/23
 */
public interface AccountService {

    /**
     * 注册账号
     *
     * @return
     */
    ResponseVO registerAccount(UserVO userVO);

    /**
     * 用户登录，登录成功会将用户信息保存再session中
     *
     * @return
     */
    User login(UserForm userForm);

    /**
     * 获取用户个人信息
     * @param id
     * @return
     */
    User getUserInfo(int id);

    /**
     * 更新用户个人信息
     * @param id
     * @param username
     * @param phonenumber
     * @return
     */
    ResponseVO updateUserInfo(int id, String username,String phonenumber);

    /**
     * 更新用户信用值
     * @param creditVO
     * @return
     * */
    ResponseVO updateCredit(CreditVO creditVO);

    /**
     * 查看用户信用值变化记录
     * @param id
     * @return
     * */
    ResponseVO checkAllCredit(int id);

    /**
     * 修改密码
     * @param passwordForm
     * @return
     */
    ResponseVO updatePassword(PasswordForm passwordForm);

    /**
     * 更新用户头像
     * @return
     */
    ResponseVO updateAvatar(AvatarVO avatarVO);
}
