package com.SnakeApp.util;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * ==============================================================
 *  Author :Amesh Senanayaka
 *  Date : 08/26/2021 - 7:05 PM
 *  Description :A Common response with payload/status used as return results
 *               of Controller/Service methods
 * ==============================================================
 **/

@Getter
@Setter
public class CommonResponse {

    private Object payload = null;
    private List<String> messages = new ArrayList<>();
    private boolean status = false;

}