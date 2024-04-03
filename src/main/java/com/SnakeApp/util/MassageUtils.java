package com.SnakeApp.util;

/* ==============================================================
 *  Author :Amesh Senanayaka
 *  Date   :08/26/2021 - 7:25 PM
 *  Description :Common Exception Massages
 * ==============================================================
 **/
public enum MassageUtils {

    CONFLIT_HOTELCODE_EXCEPTION_MASSAGE("Already Exists HOTEL Code : "),
    NOT_FOUNT_HOTEL_BY_ID_EXCEPTION("Cannot Find HOTEL By Id : "),
    NOT_FOUNT_HOTEL_BY_CODE_EXCEPTION("Cannot Find HOTEL By Code : "),
    NOT_FOUNT_HOTEL_BY_NAME_EXCEPTION("Cannot Find HOTEL By Name : "),


    HOTEL_ID_VALIDATION_EXCEPTION_MESSAGE("HOTEL Id must be a Number"),

    CONFLIT_CLIENT_NIC_EXCEPTION_MASSAGE("Already Exists NIC : "),
    NOT_FOUNT_CLIENT_BY_ID_EXCEPTION("Cannot Find Client By Id : "),
    NOT_FOUNT_CLIENT_BY_NIC_EXCEPTION("Cannot Find Client By NIC : "),


    REQUEST_SUCCESSFUL("Request is Success"),
    SUCCESSFULLY_CREATED("Successfully Created"),
    SUCCESSFULLY_UPDATED("Successfully Updated");

    private String massage;
    MassageUtils(String massage){
        this.massage=massage;
    }

    public String massage(){
        return massage;
    }

}
