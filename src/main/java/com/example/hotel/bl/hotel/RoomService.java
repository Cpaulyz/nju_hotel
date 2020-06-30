package com.example.hotel.bl.hotel;

import com.example.hotel.po.HotelRoom;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.RoomVO;

import java.util.List;

public interface RoomService {

    /**
     * 获取某个酒店的全部房间信息
     * @param hotelId
     * @return
     */
    List<HotelRoom> retrieveHotelRoomInfo(Integer hotelId);

    /**
     * 添加酒店客房信息
     * @param hotelRoom
     */
    ResponseVO insertRoomInfo(HotelRoom hotelRoom);

    /**
     * 更新房间信息
     * @param roomVO
     * @return
     */
    ResponseVO updateRoomInfo(RoomVO roomVO);

    /**
     * 删除房间
     * @param roomId
     * @return
     */
    ResponseVO deleteRoom(int roomId);
}
