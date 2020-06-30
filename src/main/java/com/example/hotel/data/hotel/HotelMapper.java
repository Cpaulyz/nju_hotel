package com.example.hotel.data.hotel;

import com.example.hotel.po.Hotel;
import com.example.hotel.vo.HotelVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HotelMapper {

    int insertHotel(Hotel hotel);

    List<HotelVO> selectAllHotel();

    HotelVO selectById(@Param("id") Integer id);

    void updateRate(@Param("hotelId") Integer hotelId);

    List<HotelVO> retrieveHotelsByManager(int managerId);

    void updateHotel(Hotel hotel);

    int deleteHotelByHotelManagerId(@Param("manager_id") Integer manager_id);
}
