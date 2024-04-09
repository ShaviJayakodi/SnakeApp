package com.SnakeApp.service;

import com.SnakeApp.dto.MailDto;
import com.SnakeApp.exeption.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    private static String fromMail = "snakeapp.web@gmail.com";
    public void sendMail(MailDto mailDto){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setText(mailDto.getMessage());
        mailMessage.setSubject(mailDto.getSubject());
        mailMessage.setTo(mailDto.getToMail());
        mailMessage.setFrom(fromMail);

        try {
            javaMailSender.send(mailMessage);
        }catch (Exception exception){
            throw new BadRequestException("Mail not sent.!");
        }
    }

}
