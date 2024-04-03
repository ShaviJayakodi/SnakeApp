package com.SnakeApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {
    private long userId;
    private String regNo;
    private String enEmail;
    private String enPassword;
    private String userType;
    private int status;
    private int isFirstLogin;
}
