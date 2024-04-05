package com.SnakeApp.service;

import com.SnakeApp.dto.AdminDto;
import com.SnakeApp.entity.Admin;
import com.SnakeApp.entity.Users;
import com.SnakeApp.enums.stakeHolderValues;
import com.SnakeApp.enums.statusValue;
import com.SnakeApp.exeption.ResourceFoundException;
import com.SnakeApp.exeption.ResourceNotFoundException;
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
import java.util.List;

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

    @Autowired
    private UsersService usersService;
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

    //Save new admin
    public CommonResponse saveNewAdmin(AdminDto adminDto) {
        Admin admin = modelMapper.map(adminDto,Admin.class);
        CommonResponse commonResponse = new CommonResponse();
        Users user = new Users();

        admin.setRegNo(commonService.generateRegNo(adminRepository.getNextAdminId(), stakeHolderValues.ADMIN.code()));
        admin.setStatus(statusValue.ACTIVE.sts());

        Admin exAdmin = adminRepository.findFirstByEmailAndStatus(adminDto.getEmail(), statusValue.ACTIVE.sts());
        if(exAdmin != null)
        {
            throw new ResourceFoundException("Email Already Exist");
        }

        Users exUser = usersRepository.findByEnEmailAndStatus(Encrypter.encrypt(adminDto.getEmail()), statusValue.ACTIVE.sts());
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
        user.setRegNo(admin.getRegNo());

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

    public CommonResponse getAllAdmin()
    {
       CommonResponse commonResponse = new CommonResponse();
       List<Admin> adminList = adminRepository.findAllByStatus(statusValue.ACTIVE.sts());
       if(adminList == null)
       {
           throw new ResourceNotFoundException("Admin Data Not Found!");
       }
       commonResponse.setPayload(adminList);
       commonResponse.setMessages(Arrays.asList("Data Found!"));
       commonResponse.setStatus(true);
       return commonResponse;
    }

    public CommonResponse getAdminByRegNo(String regNo){
        CommonResponse commonResponse = new CommonResponse();
        Admin admin = adminRepository.findByRegNoAndStatus(regNo, statusValue.ACTIVE.sts());
        if(admin == null){
            throw new ResourceNotFoundException("Admin Data Not Found For : "+regNo);
        }
        commonResponse.setPayload(admin);
        commonResponse.setMessages(Arrays.asList("Data Found for : " +regNo));
        commonResponse.setStatus(true);
        return commonResponse;
    }

    public CommonResponse updateAdmin(AdminDto adminDto){
        CommonResponse commonResponse = new CommonResponse();
        Admin admin = modelMapper.map(adminDto,Admin.class);
        if(adminRepository.findByRegNoAndStatus(admin.getRegNo(), statusValue.ACTIVE.sts()) == null){
            throw new ResourceNotFoundException("Admin Data Not Found!");
        }
        if(adminRepository.save(admin) != null)
        {
            commonResponse.setStatus(true);
            commonResponse.setMessages(Arrays.asList("Admin Data has Updated!"));
            commonResponse.setPayload(admin);
        }
        else {
            commonResponse.setStatus(false);
            commonResponse.setMessages(Arrays.asList("Data Not Saved!"));
        }
        return commonResponse;

    }

    public CommonResponse deleteAdmin(long adminId){
        CommonResponse commonResponse = new CommonResponse();
        Admin admin = adminRepository.findByAdminIdAndStatus(adminId, statusValue.ACTIVE.sts());
        if(admin == null){
            throw new ResourceNotFoundException("Admin Data Not Found!");
        }
        admin.setStatus(statusValue.DEACTIVE.sts());
        if(adminRepository.save(admin) != null){
            usersService.deleteUser(admin.getEmail());
        }
        commonResponse.setMessages(Arrays.asList("Successfully Deleted!"));
        commonResponse.setStatus(true);
        return commonResponse;
    }

}

