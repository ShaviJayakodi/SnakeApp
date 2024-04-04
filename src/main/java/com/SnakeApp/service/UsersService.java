package com.SnakeApp.service;

import com.SnakeApp.dto.LoginDto;
import com.SnakeApp.dto.PasswordChangeDto;
import com.SnakeApp.entity.Users;
import com.SnakeApp.enums.stakeHolderValues;
import com.SnakeApp.enums.statusValue;
import com.SnakeApp.exeption.GlobalExceptionHandler;
import com.SnakeApp.exeption.ResourceNotFoundException;
import com.SnakeApp.repository.UsersRepository;
import com.SnakeApp.util.CommonResponse;
import com.SnakeApp.util.Encrypter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UsersService{

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CommonService commonService;

    public CommonResponse login(LoginDto loginDto){
        CommonResponse commonResponse = new CommonResponse();
        Users user = usersRepository.findByEnEmailAndStatus(Encrypter.encrypt(loginDto.getEmail()), statusValue.ACTIVE.sts());
        if(user == null){
            throw new ResourceNotFoundException("User Not Found!");
        }
        if(user.getEnPassword().equals(Encrypter.encrypt(loginDto.getPassword())))
        {
            commonResponse.setPayload(user);
            commonResponse.setMessages(Arrays.asList("Login Successfully!"));
            commonResponse.setStatus(true);
        }
        else
        {
            commonResponse.setMessages(Arrays.asList("Incorrect Email or Password!"));
            commonResponse.setStatus(false);
        }

        return commonResponse;
    }


    public CommonResponse changePassword(PasswordChangeDto passwordChangeDto){
        CommonResponse commonResponse = new CommonResponse();
        Users user = new Users();
        user = usersRepository.findByEnEmailAndStatus(Encrypter.encrypt(passwordChangeDto.getEmail()),statusValue.ACTIVE.sts());
        if(user == null){
            throw new ResourceNotFoundException("User Not Found!");
        }
        System.out.println(user.getEnPassword());
        System.out.println(Encrypter.encrypt(passwordChangeDto.getCurPassword()));
        if(Encrypter.encrypt(passwordChangeDto.getCurPassword()) == user.getEnPassword()) {
            throw new ResourceNotFoundException("Password Not Matched!");
        }
        user.setEnPassword(Encrypter.encrypt(passwordChangeDto.getNewPassword()));
        user.setIsFirstLogin(statusValue.COMPLETE.sts());
        usersRepository.save(user);

        commonResponse.setStatus(true);
        commonResponse.setMessages(Arrays.asList("Password Changed!"));

        return commonResponse;
    }



}
