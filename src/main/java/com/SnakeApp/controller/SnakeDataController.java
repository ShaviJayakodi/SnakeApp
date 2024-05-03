package com.SnakeApp.controller;

import com.SnakeApp.service.SnakeDataService;
import com.SnakeApp.util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("snakeData")
@CrossOrigin
public class SnakeDataController {

    @Autowired
    private SnakeDataService snakeDataService;

    @GetMapping("/getSnakesByName")
    public CommonResponse getSnakeDataByName(@RequestParam String snakeName){
        return snakeDataService.getSnakeDataByName(snakeName);
    }
}
