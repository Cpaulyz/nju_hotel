package com.example.hotel.blImpl.user;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.data.user.CreditMapper;
import com.example.hotel.po.User;
import com.example.hotel.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AccountServiceImpl implements AccountService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    private final static String UPDATE_ERROR = "修改失败";
    private final static String CREDIT_ERROR = "查询失败";
    private final static String PASSWORD_ERROR ="原密码错误";
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private CreditMapper creditMapper;

    @Override
    public ResponseVO registerAccount(UserVO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO,user);
        try {
            accountMapper.createNewAccount(user);
            CreditVO creditVO=new CreditVO(user.getId());
            creditMapper.insertCredit(creditVO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public User login(UserForm userForm) {
        User user = accountMapper.getAccountByName(userForm.getEmail());
        if (null == user || !user.getPassword().equals(userForm.getPassword())) {
            return null;
        }
        return user;
    }

    @Override
    public User getUserInfo(int id) {
        User user = accountMapper.getAccountById(id);
        if (user == null) {
            return null;
        }
        return user;
    }

    @Override
    public ResponseVO updateUserInfo(int id, String username, String phonenumber) {
        try {
            accountMapper.updateAccount(id ,username, phonenumber);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO updateCredit(CreditVO creditVO) {
        try {
            accountMapper.updateCredit(creditVO.getUserId(),creditVO.getResult());
            creditMapper.insertCredit(creditVO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO checkAllCredit(int id) {
        try {
            List<CreditVO> creditVOList=creditMapper.checkAllCredit(id);
            return ResponseVO.buildSuccess(creditVOList);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(CREDIT_ERROR);
        }
    }

    @Override
    public ResponseVO updatePassword(PasswordForm passwordForm) {
        try {
            User user = accountMapper.getAccountById(passwordForm.getId());
            if(!user.getPassword().equals(passwordForm.getOldPassword())){
                // 先验证原密码对不对,字符串比较不要用!=
                return ResponseVO.buildFailure(PASSWORD_ERROR);
            }else {
                // 修改密码
                accountMapper.updatePassword(passwordForm.getId(),passwordForm.getNewPassword());
                return ResponseVO.buildSuccess(true);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
    }

    @Override
    public ResponseVO updateAvatar(AvatarVO avatarVO) {
        try {
            accountMapper.updateAvatar(avatarVO.getId(),avatarVO.getAvatar());
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            System.out.println(e);
            return ResponseVO.buildFailure(e.getMessage());
        }
    }


}
