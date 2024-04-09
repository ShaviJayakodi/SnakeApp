package com.SnakeApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SnakeCatcherDto {
    private long snakeCatcherId;
    private String regNo;
    private String salutation;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private int age;
    private String email;
    private String street;
    private String city;
    private long postalCode;
    private int status;
}
