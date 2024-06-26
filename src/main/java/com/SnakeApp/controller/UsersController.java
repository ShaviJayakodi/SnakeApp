package com.SnakeApp.controller;

import com.SnakeApp.dto.PasswordChangeDto;
import com.SnakeApp.service.UsersService;
import com.SnakeApp.util.CommonResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("user")
public class UsersController {
    @Autowired
    private UsersService usersService;

        @PostMapping("/changePassword")
        public CommonResponse changePassword(@Valid @RequestBody PasswordChangeDto passwordChangeDto){
        return usersService.changePassword(passwordChangeDto);
    }

//    @GetMapping("/passwordVerify")
//    public CommonResponse passwordVerify(@Valid @RequestParam long userId, @RequestParam String password){
//        return usersService.passwordVerify(userId, password);
//    }
}
