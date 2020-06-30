package com.example.hotel.data.hotel;

import com.example.hotel.enums.RoomType;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.RoomVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoomMapperTest {
    @Autowired
    RoomMapper roomMapper;

    @Test
    public void insertRoomTest(){
        HotelRoom hotelRoom=new HotelRoom();
        hotelRoom.setRoomType(RoomType.BigBed);
        hotelRoom.setHotelId(1);
        hotelRoom.setPrice(300.0);
        hotelRoom.setTotal(20);
        roomMapper.insertRoom(hotelRoom);
        assert hotelRoom.getId()!=null;
    }

    @Test
    public void selectRoomsByHotelIdTest(){
        HotelRoom hotelRoom=new HotelRoom();
        hotelRoom.setRoomType(RoomType.BigBed);
        hotelRoom.setHotelId(0);
        hotelRoom.setPrice(300.0);
        hotelRoom.setTotal(20);
        roomMapper.insertRoom(hotelRoom);
        List<HotelRoom> hotelRooms = roomMapper.selectRoomsByHotelId(0);
        HotelRoom room = hotelRooms.get(0);
        assert hotelRoom.getId()==room.getId();
        assert hotelRoom.getRoomType().equals(room.getRoomType());
        assert hotelRoom.getPrice() == room.getPrice();
        assert hotelRoom.getTotal() == room.getTotal();
    }

    @Test
    public void updateRoomInfoTest(){
        HotelRoom hotelRoom=new HotelRoom();
        hotelRoom.setRoomType(RoomType.BigBed);
        hotelRoom.setHotelId(0);
        hotelRoom.setPrice(300.0);
        hotelRoom.setTotal(20);
        roomMapper.insertRoom(hotelRoom);
        RoomVO roomVO = new RoomVO();
        roomVO.setId(hotelRoom.getId());
        roomVO.setPrice(199.9);
        roomVO.setTotal(10);
        roomMapper.updateRoomInfo(roomVO);
        List<HotelRoom> hotelRooms = roomMapper.selectRoomsByHotelId(0);
        HotelRoom room = hotelRooms.get(0);
        assert room.getId()==roomVO.getId();
        assert room.getPrice() == roomVO.getPrice();
        assert room.getTotal() == roomVO.getTotal();
    }

    @Test
    public void deleteRoomByIdTest(){
        HotelRoom hotelRoom=new HotelRoom();
        hotelRoom.setRoomType(RoomType.BigBed);
        hotelRoom.setHotelId(0);
        hotelRoom.setPrice(300.0);
        hotelRoom.setTotal(20);
        roomMapper.insertRoom(hotelRoom);
        roomMapper.deleteRoomById(hotelRoom.getId());
        List<HotelRoom> hotelRooms = roomMapper.selectRoomsByHotelId(0);
        assert hotelRooms.size()==0;
    }
}
