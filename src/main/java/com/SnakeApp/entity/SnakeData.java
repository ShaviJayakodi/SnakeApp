package com.SnakeApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SnakeData {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long snakeId;
    private String snakeName;
    private int venomousOrNot;
    private int venomousLevel;
    private int status;

}
