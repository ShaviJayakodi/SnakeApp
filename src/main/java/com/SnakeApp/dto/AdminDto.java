package com.SnakeApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {
    private long adminId;
    private String regNo;
    private String salutation;
    private String firstName;
    private String lastName;
    private String email;
    private int status;
}
