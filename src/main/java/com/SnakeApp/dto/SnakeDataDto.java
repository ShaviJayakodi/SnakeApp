package com.SnakeApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SnakeDataDto {
    private long snakeId;
    private String snakeName;
    private int venomousOrNot;
    private int venomousLevel;
    private int status;
}
