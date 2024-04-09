package com.SnakeApp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
@Transactional
public class CommonService {

    public String generateRegNo(int nextId ,String code)
    {
        String regNo="";
        LocalDateTime currantDate = LocalDateTime.now();
        LocalDate date1 = currantDate.toLocalDate();
        int year1 =date1.getYear();
        int year= Integer.parseInt((Integer.toString(year1)).substring(2,4));
        String pattern = "0000";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        String format = decimalFormat.format(nextId);
        regNo=Integer.toString(year)+code+format;
        return regNo;

    }

    public int calculateAge(LocalDate dateOfBirth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //LocalDate dateOfBirth = LocalDate.parse(dateOfBirthStr, formatter);
        LocalDate currentDate = LocalDate.now();
       //boolean isAfterBirthday = (currentDate.getMonthValue() > dateOfBirth.getMonthValue() ||
          //      (currentDate.getMonthValue() == dateOfBirth.getMonthValue() &&
          //              currentDate.getDayOfMonth() > dateOfBirth.getDayOfMonth()));
        int age = Period.between(dateOfBirth, currentDate).getYears();
        return age;
    }
}
