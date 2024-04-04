package com.SnakeApp.controller;

import com.SnakeApp.dto.LoginDto;
import com.SnakeApp.service.UsersService;
import com.SnakeApp.util.CommonResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("snakeapp")
public class LoginController {
    @Autowired
    private UsersService usersService;

    @GetMapping("/login")
    public CommonResponse login(@Valid @RequestBody LoginDto loginDto){
        return usersService.login(loginDto);
    }
}
