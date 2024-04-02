package com.SnakeApp.exeption;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
/* ==============================================================
 *  Author :Savishka Dilshan
 *  Date   : 02/04/2024 - 00:45 AM
 *  Description :A Common response with error code/details used as return results
 *               of throwing exception
 * ==============================================================
 **/
@Getter
@Setter
public class ErrorResponse {
    private Date timestamp;
    private int statusCode;
    private String message;
    private String details;
}
