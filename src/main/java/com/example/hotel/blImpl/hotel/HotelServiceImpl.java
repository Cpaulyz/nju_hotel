package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.hotel.CommentMapper;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.enums.HotelStar;
import com.example.hotel.enums.OrderStateType;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.*;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoomService roomService;


    @Override
    public ResponseVO addHotel(HotelVO hotelVO){
        try {
            User manager = accountService.getUserInfo(hotelVO.getManagerId());
            if(manager == null || !manager.getUserType().equals(UserType.HotelManager)){
                return ResponseVO.buildFailure("管理员不存在或者无权限！创建酒店失败！");
            }
            Hotel hotel = new Hotel();
            hotel.setDescription(hotelVO.getDescription());
            hotel.setAddress(hotelVO.getAddress());
            hotel.setHotelName(hotelVO.getName());
            hotel.setPhoneNum(hotelVO.getPhoneNum());
            hotel.setManagerId(hotelVO.getManagerId());
            hotel.setRate(5.0);     // 创建时要都给初始值5 后面会自动更新
            hotel.setBizRegion(hotelVO.getBizRegion());
            hotel.setHotelStar(HotelStar.valueOf(hotelVO.getHotelStar()));
            hotelMapper.insertHotel(hotel);
            return ResponseVO.buildSuccess(hotel);
        }catch (Exception e){
            System.out.println(e);
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

    @Override
    public ResponseVO updateHotel(HotelVO hotelVO){
        try {
            Hotel hotel = new Hotel();
            hotel.setDescription(hotelVO.getDescription());
            hotel.setAddress(hotelVO.getAddress());
            hotel.setBizRegion(hotelVO.getBizRegion());
            hotel.setId(hotelVO.getId());
            hotel.setPicture(hotelVO.getPicture());
            // 按理来说只有这四个信息可以改，如果需求有变的话mapper也要跟着变
            hotelMapper.updateHotel(hotel);
            return ResponseVO.buildSuccess(hotel);
        }catch (Exception e){
            System.out.println(e);
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

    @Override
    public List<RoomVO> getHotelRooms(HotelRoomsVO hotelRoomsVO) {
        List<HotelRoom> rooms = roomService.retrieveHotelRoomInfo(hotelRoomsVO.getHotelId());
        List<RoomVO> roomVOS = new ArrayList<>();
        for(HotelRoom hotelRoom:rooms){
            RoomVO roomVO=new RoomVO(hotelRoom);
            int total=roomVO.getTotal();
            int minCurNum=roomVO.getTotal();
            //算从checkIn到checkOut这个时间段的所有的curNum 取min
            LocalDate checkInDate=hotelRoomsVO.getCheckInDate();
            LocalDate checkOutDate=hotelRoomsVO.getCheckOutDate();
            while (checkInDate.compareTo(checkOutDate)<0){
                String checkIn=checkInDate.toString();
                Integer orderNum=orderMapper.getOrderNumByDate(hotelRoom.getHotelId(),hotelRoom.getRoomType(),checkIn);
                if(orderNum!=null){
                    int tempNum=total-orderNum;
                    if(tempNum<minCurNum)
                        minCurNum=tempNum;
                }
                checkInDate=checkInDate.plusDays(1);
            }
            roomVO.setCurNum(minCurNum);
            roomVOS.add(roomVO);
        }
        return roomVOS;
}

    @Override
    public List<HotelVO> retrieveHotels() {
        return hotelMapper.selectAllHotel();
    }

    @Override
    public HotelVO retrieveHotelDetails(Integer hotelId) {
        HotelVO hotelVO = hotelMapper.selectById(hotelId);       // 没有rooms
        return hotelVO;
    }

    @Override
    public List<HotelVO> retrieveHotelsByManager(int managerId) {
        return hotelMapper.retrieveHotelsByManager(managerId);
    }

    @Override
    public ResponseVO addComment(CommentVO commentVO){
        try {
            commentMapper.addComment(commentVO);
            orderMapper.updateOrderState(commentVO.getOrderId(), OrderStateType.Evaluated);   //订单已评价
            hotelMapper.updateRate(commentVO.getHotelId());    // 每次评价都更新酒店评分
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure("添加评价失败");
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO checkComment(int hotelId) {
        List<Comment> comments=null;
        try {
            comments=commentMapper.checkComment(hotelId);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure("查询失败");
        }
        if(comments==null)
            return ResponseVO.buildFailure("暂时还没有任何评价");
        return ResponseVO.buildSuccess(comments);
    }

}
