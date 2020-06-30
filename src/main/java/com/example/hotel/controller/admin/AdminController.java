package com.example.hotel.controller.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.blImpl.admin.AdminServiceImpl;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@RestController()
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @PostMapping("/addManager")
    public ResponseVO addManager(@RequestBody UserVO user) {
        return adminService.addManager(user);
    }

    @PostMapping("/addHotelManager")
    public ResponseVO addHotelManager(@RequestBody UserVO user) {
        return adminService.addHotelManager(user);
    }

    @PostMapping("/getAllManagers") // 这个好像已经没用了，但是不确定，先放着吧
    public ResponseVO getAllManagers() {
        return ResponseVO.buildSuccess(adminService.getAllManagers());
    }

    @PostMapping("/getAllUsers")
    public ResponseVO getAllUsers() {
        return ResponseVO.buildSuccess(adminService.getAllUsers());
    }

    @GetMapping("/{userId}/resetPassword")
    public ResponseVO resetPassword(@PathVariable int userId) {
        return ResponseVO.buildSuccess(adminService.resetPassword(userId));
    }
    @GetMapping("/deleteUser/{userId}")
    public ResponseVO deleteUser(@PathVariable int userId) {
        return adminService.deleteUser(userId);
    }

}