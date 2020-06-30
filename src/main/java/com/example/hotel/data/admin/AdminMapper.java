package com.example.hotel.data.admin;

import com.example.hotel.po.Hotel;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.vo.HotelVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Mapper
@Repository
public interface AdminMapper {

    int addUser(User user);

    List<User> getAllManagers();

//    List<HotelVO> getAllHotels(@Param("managerId") int managerId);

    List<User> getAllUsers();

    void resetPassword(int userId);
}
