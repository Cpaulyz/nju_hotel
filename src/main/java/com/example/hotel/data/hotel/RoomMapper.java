package com.example.hotel.data.hotel;

import com.example.hotel.po.HotelRoom;
import com.example.hotel.vo.RoomVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoomMapper {

    int insertRoom(HotelRoom hotelRoom);

    List<HotelRoom> selectRoomsByHotelId(@Param("hotelId") Integer hotelId);

    void updateRoomInfo(RoomVO roomVO);

    void deleteRoomById(int roomId);
}
