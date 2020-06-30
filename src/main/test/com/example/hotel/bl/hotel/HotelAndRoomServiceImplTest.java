package com.example.hotel.bl.hotel;

import com.example.hotel.HotelApplication;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.enums.HotelStar;
import com.example.hotel.enums.RoomType;
import com.example.hotel.po.Comment;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.vo.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HotelApplication.class)
@Transactional
public class HotelAndRoomServiceImplTest {
    @Autowired
    HotelService hotelService;
    @Autowired
    RoomService roomService;

    @Test
    public void addHotelTest(){
        HotelVO hotelVO=new HotelVO();
        hotelVO.setName("test");
        hotelVO.setAddress("haven");
        hotelVO.setBizRegion("paradise");
        hotelVO.setHotelStar("Five");
        hotelVO.setDescription("cool");
        hotelVO.setPhoneNum("2333333");
        hotelVO.setManagerId(9);
        ResponseVO responseVO=hotelService.addHotel(hotelVO);
        assert responseVO.getSuccess();
        hotelVO.setId(((Hotel)responseVO.getContent()).getId());
        hotelVO.setBizRegion("PARADISE");
        responseVO=hotelService.updateHotel(hotelVO);
        assert responseVO.getSuccess();
        hotelVO.setDescription("COOL");
        responseVO=hotelService.updateHotel(hotelVO);
        assert responseVO.getSuccess();
        List<HotelVO> list=hotelService.retrieveHotels();
        assert list.get(list.size()-1).getName().equals("test");
        HotelVO hotelVO1=hotelService.retrieveHotelDetails(list.get(list.size()-1).getId());
        assert hotelVO1.getName().equals("test");
        list=hotelService.retrieveHotelsByManager(9);
        assert list.get(list.size()-1).getName().equals("test");
    }

    @Test
    public void hotelRoomTest(){
        HotelRoom hotelRoom=new HotelRoom();
        hotelRoom.setRoomType(RoomType.BigBed);
        hotelRoom.setHotelId(1);
        hotelRoom.setPrice(300.0);
        hotelRoom.setTotal(20);
        ResponseVO responseVO=roomService.insertRoomInfo(hotelRoom);
        assert responseVO.getSuccess();
        RoomVO roomVO=new RoomVO(hotelRoom);
        roomVO.setCurNum(10);
        responseVO=roomService.updateRoomInfo(roomVO);
        assert responseVO.getSuccess();
        HotelRoomsVO hotelRoomsVO=new HotelRoomsVO();
        hotelRoomsVO.setHotelId(1);
        hotelRoomsVO.setCheckInDate(LocalDate.now());
        hotelRoomsVO.setCheckOutDate(LocalDate.now().plusDays(1));
        List<RoomVO> list=hotelService.getHotelRooms(hotelRoomsVO);
        assert list.size()!=0;
        responseVO=roomService.deleteRoom(hotelRoom.getId());
        assert responseVO.getSuccess();
    }

    @Test
    public void commentTest(){
        CommentVO commentVO=new CommentVO();
        commentVO.setCreateTime("2020-06-22");
        commentVO.setHotelId(1);
        commentVO.setOrderId(1);
        commentVO.setUserName("王雷");
        commentVO.setGrade(2);
        commentVO.setContent("就这？");
        ResponseVO responseVO=hotelService.addComment(commentVO);
        assert responseVO.getSuccess();
        responseVO=hotelService.checkComment(1);
        assert responseVO.getContent()!=null;
    }
}
