package com.SnakeApp.service;

import com.SnakeApp.dto.SnakeDataDto;
import com.SnakeApp.entity.SnakeData;
import com.SnakeApp.enums.statusValue;
import com.SnakeApp.repository.SnakeDataRepository;
import com.SnakeApp.util.CommonResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class SnakeDataService {

    @Autowired
    private SnakeDataRepository snakeDataRepository;
    @Autowired
    private ModelMapper modelMapper;

    public CommonResponse getSnakeDataByName(String snakeName) {
        CommonResponse commonResponse = new CommonResponse();
        SnakeData snakeData = snakeDataRepository.getSnakeDataBySnakeNameAndStatus(snakeName, statusValue.ACTIVE.sts());
        SnakeDataDto snakeDataDto = modelMapper.map(snakeData,SnakeDataDto.class);

        snakeDataDto.setSnakeName(capitalizeEachWord(snakeData.getSnakeName()));
        //set venomous or non-venomous
        if(snakeData.getVenomousOrNot()==1){
            snakeDataDto.setVenomousOrNot("Venomous");
        }
        else{
            snakeDataDto.setVenomousOrNot("Non-Venomous");
        }

        //set venomous level
        if(snakeData.getVenomousLevel()==0){
            snakeDataDto.setVenomousLevel("Non");
        }
        else if(snakeData.getVenomousLevel()==1){
            snakeDataDto.setVenomousLevel("Low");
        }
        else if(snakeData.getVenomousLevel()==2){
            snakeDataDto.setVenomousLevel("Mildly");
        }
        else if(snakeData.getVenomousLevel()==3){
            snakeDataDto.setVenomousLevel("High");
        }

        commonResponse.setStatus(true);
        commonResponse.setMessages(Arrays.asList("Snake Data Found!"));
        commonResponse.setPayload(snakeDataDto);
        return commonResponse;

    }

    public static String capitalizeEachWord(String sentence) {
        String[] words = sentence.split(" ");
        StringBuilder sb = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            } else {
                sb.append(" ");
            }
        }

        return sb.toString().trim();
    }
}
