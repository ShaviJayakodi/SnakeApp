package com.SnakeApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SnakeCatcherDto {
    private long id;
    private String regNo;
    private String salutation;
    private String firstName;
    private String lastName;
    private Date dob;
    private String Street;
    private String City;
    private long postalCode;
    private int status;
}
