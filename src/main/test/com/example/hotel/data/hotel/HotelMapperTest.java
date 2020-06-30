package com.example.hotel.data.hotel;

import com.example.hotel.enums.HotelStar;
import com.example.hotel.po.Hotel;
import com.example.hotel.vo.HotelVO;
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
public class HotelMapperTest {
    @Autowired
    HotelMapper hotelMapper;

    @Test
    public void insertHotelTest(){
        Hotel hotel = new Hotel();
        hotel.setAddress("address");
        hotel.setBizRegion("area");
        hotel.setDescription("description");
        hotel.setHotelName("名字");
        hotel.setHotelStar(HotelStar.Five);
        hotel.setPhoneNum("13159175930");
        hotel.setRate(5.0);
        hotel.setManagerId(7);
        hotelMapper.insertHotel(hotel);
        assert hotel.getId()!=null;
    }

    @Test
    public void selectAllHotelTest(){
        List<HotelVO> hotelVOS  = hotelMapper.selectAllHotel();
        assert hotelVOS.size()>0;
    }

    @Test
    public void selectByIdTest(){
        Hotel hotel = new Hotel();
        hotel.setAddress("address");
        hotel.setBizRegion("area");
        hotel.setDescription("description");
        hotel.setHotelName("名字");
        hotel.setHotelStar(HotelStar.Five);
        hotel.setPhoneNum("13159175930");
        hotel.setRate(5.0);
        hotel.setManagerId(7);
        hotelMapper.insertHotel(hotel);
        HotelVO hotelVO = hotelMapper.selectById(hotel.getId());
        assert hotel.getHotelName().equals(hotelVO.getName());
    }

    @Test
    public void updateHotel(){
        Hotel hotel = new Hotel();
        hotel.setAddress("address");
        hotel.setBizRegion("area");
        hotel.setDescription("description");
        hotel.setHotelName("名字");
        hotel.setHotelStar(HotelStar.Five);
        hotel.setPhoneNum("13159175930");
        hotel.setRate(5.0);
        hotel.setManagerId(7);
        hotelMapper.insertHotel(hotel);
        hotel.setAddress("地址");
        hotel.setDescription("描述");
        hotel.setBizRegion("江苏省 南京市");
        // 只能改这三个
        hotelMapper.updateHotel(hotel);
        HotelVO hotelVO = hotelMapper.selectById(hotel.getId());
        assert hotelVO.getAddress().equals(hotel.getAddress());
        assert hotelVO.getBizRegion().equals(hotel.getBizRegion());
        assert hotelVO.getDescription().equals(hotel.getDescription());
    }

    @Test
    public void retrieveHotelsByManagerTest(){
        List<HotelVO> hotelVOS  = hotelMapper.retrieveHotelsByManager(6);
        assert hotelVOS.size()==3;
    }
}
