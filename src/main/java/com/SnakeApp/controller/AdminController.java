package com.SnakeApp.controller;

import com.SnakeApp.dto.AdminDto;
import com.SnakeApp.service.AdminService;
import com.SnakeApp.service.CommonService;
import com.SnakeApp.util.CommonResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
@CrossOrigin
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/newAdmin")
    public CommonResponse saveNewAdmin (@Valid @RequestBody AdminDto adminDto)
    {
        return adminService.saveNewAdmin(adminDto);
    }

    @GetMapping("/getAllAdmins")
    public CommonResponse getAllAdmin()
    {
        return adminService.getAllAdmin();
    }

    @GetMapping("/getAdminByRegNo")
    public CommonResponse getAdminByRegNo(@RequestParam String regNo){
        return adminService.getAdminByRegNo(regNo);
    }

    @PutMapping ("/updateAdmin")
    public CommonResponse updateAdmin(@Valid @RequestBody AdminDto adminDto){
        return adminService.updateAdmin(adminDto);
    }

    @DeleteMapping("/deleteAdmin")
    public CommonResponse deleteAdmin(@RequestParam long adminId){
        return adminService.deleteAdmin(adminId);
    }


}
