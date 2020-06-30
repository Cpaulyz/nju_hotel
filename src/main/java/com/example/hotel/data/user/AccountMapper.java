package com.example.hotel.data.user;

import com.example.hotel.po.User;
import com.example.hotel.vo.CreditVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface AccountMapper {

    /**
     * 创建一个新的账号
     *
     * @return
     */
     int createNewAccount(User user);

    /**
     * 根据用户名查找账号
     * @param email
     * @return
     */
     User getAccountByName(@Param("email") String email);

     User getAccountById(@Param("id") int id);

    /**
     * 更新用户信息
     * @param id
     * @param username
     * @param phonenumber
     * @return
     */
     int updateAccount(@Param("id") int id,@Param("userName") String username, @Param("phoneNumber") String phonenumber);

     /**
      * 更新用户信用值
      * @param result
      * @return
      * */
     int updateCredit(@Param("id") int userId, @Param("result") double result);

     int updatePassword(@Param("id") int userId,@Param("password") String password);

     int deleteUserById(@Param("id") int id);

     void updateAvatar(@Param("id")int userId, @Param("url")String url);
}
