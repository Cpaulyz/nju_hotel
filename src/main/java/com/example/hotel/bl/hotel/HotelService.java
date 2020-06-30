package com.example.hotel.bl.hotel;

import com.example.hotel.po.HotelRoom;
import com.example.hotel.po.Order;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.*;

import java.util.List;

public interface HotelService {

    /**
     * 添加酒店
     *
     * @param hotelVO
     * @throws
     */
    ResponseVO addHotel(HotelVO hotelVO);

    /**
     * 更新酒店信息
     *
     * @param hotelVO
     * @throws ServiceException
     */
    ResponseVO updateHotel(HotelVO hotelVO);


    /**
     * 列表获取酒店信息
     *
     * @return
     */
    List<HotelVO> retrieveHotels();

    /**
     * 获取某家酒店详细信息
     *
     * @param hotelId
     * @return
     */
    HotelVO retrieveHotelDetails(Integer hotelId);

    /**
     * 查看酒店剩余房间信息
     *
     * @param hotelRoomsVO
     * @return
     */
    List<RoomVO> getHotelRooms(HotelRoomsVO hotelRoomsVO);

    /**
     * 评价酒店
     *
     * @param commentVO
     * @return
     */
    ResponseVO addComment(CommentVO commentVO);

    /**
     * 根据酒店id查看所有评价
     *
     * @param hotelId
     * @return
     */
    ResponseVO checkComment(int hotelId);

    List<HotelVO> retrieveHotelsByManager(int managerId);
}
