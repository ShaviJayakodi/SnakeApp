package com.SnakeApp.controller;

import com.SnakeApp.dto.LoginDto;
import com.SnakeApp.service.UsersService;
import com.SnakeApp.util.CommonResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("snakeapp")
@CrossOrigin
public class LoginController {
    @Autowired
    private UsersService usersService;

    @PostMapping("/login")
    public CommonResponse login(@Valid @RequestBody LoginDto loginDto){
        return usersService.login(loginDto);
    }
}
