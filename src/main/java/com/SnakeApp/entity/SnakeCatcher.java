package com.SnakeApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SnakeCatcher {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
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
