package com.example.hotel.controller.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private RoomService roomService;


    @PostMapping("/addHotel")
    public ResponseVO createHotel(@RequestBody HotelVO hotelVO) throws ServiceException {
        return hotelService.addHotel(hotelVO);
    }

    @PostMapping("/updateHotel")
    public ResponseVO updateHotel(@RequestBody HotelVO hotelVO) throws ServiceException {
        return hotelService.updateHotel(hotelVO);
    }

    @GetMapping("/all")
    public ResponseVO retrieveAllHotels(){
        return ResponseVO.buildSuccess(hotelService.retrieveHotels());
    }

    @PostMapping("/roomInfo")
    public ResponseVO addRoomInfo(@RequestBody HotelRoom hotelRoom) {
        return roomService.insertRoomInfo(hotelRoom);
    }

    @GetMapping("/{hotelId}/detail")
    public ResponseVO retrieveHotelDetail(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(hotelService.retrieveHotelDetails(hotelId));
    }

    @PostMapping("/getHotelRooms")
    public ResponseVO getHotelRooms(@RequestBody HotelRoomsVO hotelRoomsVO){
        return ResponseVO.buildSuccess(hotelService.getHotelRooms(hotelRoomsVO));
    }

    @PostMapping("/addComment")
    public ResponseVO addComment(@RequestBody CommentVO commentVO){
        return hotelService.addComment(commentVO);
    }

    @GetMapping("/{hotelId}/checkComment")
    public ResponseVO checkComment(@PathVariable Integer hotelId){
        return hotelService.checkComment(hotelId);
    }


    @GetMapping("/{managerId}/getManagerHotels")
    public ResponseVO retrieveHotelsByManager(@PathVariable int managerId){
        return ResponseVO.buildSuccess(hotelService.retrieveHotelsByManager(managerId));
    }

    @PostMapping("/updateRoomInfo")
    public ResponseVO updateRoomInfo(@RequestBody RoomVO roomVO){
        return roomService.updateRoomInfo(roomVO);
    }

    @GetMapping("/{roomId}/deleteRoom")
    public ResponseVO deleteRoom(@PathVariable int roomId){
        return roomService.deleteRoom(roomId);
    }
}
