package com.SnakeApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SnakeCatcher {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
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
