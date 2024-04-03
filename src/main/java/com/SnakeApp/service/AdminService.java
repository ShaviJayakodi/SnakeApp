package com.SnakeApp.service;

import com.SnakeApp.dto.AdminDto;
import com.SnakeApp.entity.Admin;
import com.SnakeApp.entity.Users;
import com.SnakeApp.enums.stakeHolderValues;
import com.SnakeApp.enums.statusValue;
import com.SnakeApp.exeption.ResourceFoundException;
import com.SnakeApp.repository.AdminRepository;
import com.SnakeApp.repository.UsersRepository;
import com.SnakeApp.util.CommonResponse;
import com.SnakeApp.util.Encrypter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@Transactional
public class AdminService implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public void run(String... args) throws Exception {
        if(adminRepository.count() == 0)
        {
            AdminDto admin = new AdminDto();
            admin.setFirstName("SUPER ADMIN");
            admin.setStatus(statusValue.ACTIVE.sts());
            admin.setEmail("admin@snakeapp.lk");
            admin.setRegNo( commonService.generateRegNo(adminRepository.getNextAdminId(), stakeHolderValues.ADMIN.code()));
            saveNewAdmin(admin);
        }
    }

    public CommonResponse saveNewAdmin(AdminDto adminDto)
    {
        Admin admin = modelMapper.map(adminDto,Admin.class);
        CommonResponse commonResponse = new CommonResponse();
        Users user = new Users();

        admin.setRegNo(commonService.generateRegNo(adminRepository.getNextAdminId(), stakeHolderValues.ADMIN.code()));
        admin.setStatus(statusValue.ACTIVE.sts());

        Admin exAdmin = adminRepository.findByEmail(adminDto.getEmail());
        if(exAdmin != null)
        {
            throw new ResourceFoundException("Email Already Exist");
        }

        Users exUser = usersRepository.findByEnEmail(Encrypter.encrypt(adminDto.getEmail()));
        if(exUser != null)
        {
            throw new ResourceFoundException("User Already Exist");
        }

        user.setIsFirstLogin(statusValue.ACTIVE.sts());
        if(adminRepository.count() == 0)
        {
            user.setUserType("SUPER_ADMIN");
        }
        else
        {
            user.setUserType("ADMIN");
        }
        user.setStatus(statusValue.ACTIVE.sts());
        user.setEnPassword(Encrypter.encrypt(adminDto.getRegNo()+"@#Snake"));
        user.setEnEmail(Encrypter.encrypt(adminDto.getEmail()));
        user.setRegNo(adminDto.getRegNo());

        if(adminRepository.save(admin) != null){
            if(usersRepository.save(user) != null)
            {
                commonResponse.setStatus(true);
                commonResponse.setMessages(Arrays.asList("New Admin Created!"));
            }
        }
        commonResponse.setPayload(admin);
        return commonResponse;
    }
}