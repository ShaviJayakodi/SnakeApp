package com.SnakeApp.service;

import com.SnakeApp.entity.Users;
import com.SnakeApp.enums.stakeHolderValues;
import com.SnakeApp.enums.statusValue;
import com.SnakeApp.repository.UsersRepository;
import com.SnakeApp.util.Encrypter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService{

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CommonService commonService;



}
