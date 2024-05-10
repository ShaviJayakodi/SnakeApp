package com.SnakeApp.controller;

import com.SnakeApp.dto.SnakeCatcherDto;
import com.SnakeApp.service.SnakeCatcherService;
import com.SnakeApp.util.CommonResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("snakeCatcher")
@CrossOrigin
public class SnakeCatcherController {
    @Autowired
    private SnakeCatcherService snakeCatcherService;

    @PostMapping("/makingAnRequest")
    public CommonResponse makingAnRequestToRegister(@Valid @RequestBody SnakeCatcherDto snakeCatcherDto){
        return snakeCatcherService.makingAnRequestToRegister(snakeCatcherDto);
    }

    @GetMapping("/getPendingApprovals")
    public CommonResponse getPendingApprovals (){
        return snakeCatcherService.getPendingApprovals();
    }

    @PutMapping("/approve")
    public CommonResponse approve (@RequestParam long snakeCatcherId, @RequestParam int status){
        return snakeCatcherService.approve(snakeCatcherId,status);
    }

    @PutMapping("/update")
    public CommonResponse updateSnakeCatcher (@Valid @RequestBody SnakeCatcherDto snakeCatcherDto){
        return snakeCatcherService.updateSnakeCatcher(snakeCatcherDto);
    }

    @GetMapping("/getAllSnakeCatchers")
    public CommonResponse getAllSnakeCatchers (){
        return snakeCatcherService.getAllSnakeCatchers();
    }

    @GetMapping("/getAllSnakeCatchersByCity")
    public CommonResponse getSnakeCatchersByCity(@RequestParam String city){
        return snakeCatcherService.getSnakeCatchersByCity(city);
    }


    @GetMapping("/getSnakeCatherDataByRegNo")
    public CommonResponse getSnakeCatherDataByRegNo(@RequestParam String regNo) {
        return snakeCatcherService.getSnakeCatherDataByRegNo(regNo);

    }
    @DeleteMapping("/deleteSnakeCatcher")
    public CommonResponse deleteSnakeCatcher(@RequestParam long snakeCatcherId){
        return snakeCatcherService.deleteSnakeCatcher(snakeCatcherId);
    }
}
