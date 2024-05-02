package com.SnakeApp.service;

import com.SnakeApp.dto.MailDto;
import com.SnakeApp.dto.SnakeCatcherDto;
import com.SnakeApp.entity.SnakeCatcher;
import com.SnakeApp.entity.Users;
import com.SnakeApp.enums.stakeHolderValues;
import com.SnakeApp.enums.statusValue;
import com.SnakeApp.exeption.ResourceFoundException;
import com.SnakeApp.exeption.ResourceNotFoundException;
import com.SnakeApp.repository.SnakeCatcherRepository;
import com.SnakeApp.repository.UsersRepository;
import com.SnakeApp.util.CommonResponse;
import com.SnakeApp.util.Encrypter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SnakeCatcherService {

    @Autowired
    private CommonService commonService;

    @Autowired
    private SnakeCatcherRepository snakeCatcherRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private EmailService emailService;

    public CommonResponse makingAnRequestToRegister(SnakeCatcherDto snakeCatcherDto){
        CommonResponse commonResponse = new CommonResponse();
        MailDto mailDto = new MailDto();
        SnakeCatcher snakeCatcher = modelMapper.map(snakeCatcherDto,SnakeCatcher.class);
        SnakeCatcher exSnakeCather = snakeCatcherRepository.findByEmailAndStatus(snakeCatcherDto.getEmail(), statusValue.ACTIVE.sts());
        SnakeCatcher ifPendingRequest = snakeCatcherRepository.findByEmailAndStatus(snakeCatcherDto.getEmail(), statusValue.PENDING.sts());
        Users user = new Users();
        if(exSnakeCather != null){
            throw new ResourceFoundException("Already Exist Email Address!");
        }
        if(ifPendingRequest != null){
            throw new ResourceNotFoundException("Your Email Already Exist and Under the Approval.!");
        }

        snakeCatcher.setRegNo(commonService.generateRegNo(snakeCatcherRepository.getNextSnakeCatcherId(), stakeHolderValues.SNAKE_CATCHER.code()));
        snakeCatcher.setAge(commonService.calculateAge(snakeCatcher.getDob()));
        snakeCatcher.setStatus(statusValue.PENDING.sts());

        user.setUserName(snakeCatcher.getSalutation()+" "+snakeCatcher.getFirstName()+" "+snakeCatcher.getLastName());
        user.setRegNo(snakeCatcher.getRegNo());
        user.setUserType("SNAKE_CATCHER");
        user.setEnEmail(Encrypter.encrypt(snakeCatcher.getEmail()));
        user.setEnPassword(Encrypter.encrypt(snakeCatcher.getRegNo()+"@#Snake"));
        user.setStatus(statusValue.PENDING.sts());
        user.setIsFirstLogin(statusValue.ACTIVE.sts());

        if(snakeCatcherRepository.save(snakeCatcher) != null){
            if(usersRepository.save(user) != null){
                mailDto.setToMail(snakeCatcher.getEmail());
                mailDto.setSubject("Register Request");
                mailDto.setMessage("Your Request Under the Admin Approval.");
                emailService.sendMail(mailDto);

                commonResponse.setPayload(snakeCatcher);
                commonResponse.setMessages(Arrays.asList("User Request has been submitted! Please Check Your E-mails!"));

            }
        }

        return commonResponse;
    }

    public CommonResponse getPendingApprovals() {
        CommonResponse commonResponse = new CommonResponse();
        List<SnakeCatcher> pendingList = snakeCatcherRepository.findByStatus(statusValue.PENDING.sts());
        if(pendingList.size() == 0){
            throw new ResourceNotFoundException("No any pending requests.!");
        }
        commonResponse.setPayload(pendingList);
        commonResponse.setStatus(true);
        commonResponse.setMessages(Arrays.asList("Data Found!"));
        return commonResponse;
    }

    public CommonResponse approve(long snakeCatcherId, int status) {
        CommonResponse commonResponse = new CommonResponse();
        MailDto mailDto = new MailDto();
        SnakeCatcher snakeCatcher = snakeCatcherRepository.findBySnakeCatcherIdAndStatus(snakeCatcherId, statusValue.PENDING.sts());
        if(snakeCatcher == null){
            throw new ResourceNotFoundException("Data Not Found.!");
        }
        Users user = usersRepository.findByRegNo(snakeCatcher.getRegNo());

        if(user == null){
            throw new ResourceNotFoundException("Data Not Found.!");
        }
        mailDto.setToMail(snakeCatcher.getEmail());
        mailDto.setSubject("Admin Approval!");
        if(status == 1){
            snakeCatcher.setStatus(statusValue.ACTIVE.sts());
            user.setStatus(statusValue.ACTIVE.sts());
            commonResponse.setMessages(Arrays.asList("Approved!"));
            mailDto.setMessage("Approved!\nPlease login with your E-mail and use password as "+Encrypter.decrypt(user.getEnPassword()));
        }
        else
        {
            snakeCatcher.setStatus(statusValue.DEACTIVE.sts());
            user.setStatus(statusValue.DEACTIVE.sts());
            commonResponse.setMessages(Arrays.asList("Rejected!"));
            mailDto.setMessage("Rejected!");
        }

        snakeCatcherRepository.save(snakeCatcher);
        usersRepository.save(user);
        emailService.sendMail(mailDto);

        return commonResponse;
    }

    public CommonResponse updateSnakeCatcher(SnakeCatcherDto snakeCatcherDto){
        SnakeCatcher snakeCatcher = modelMapper.map(snakeCatcherDto,SnakeCatcher.class);
        snakeCatcher.setAge(commonService.calculateAge(snakeCatcherDto.getDob()));
        CommonResponse commonResponse = new CommonResponse();
        MailDto mailDto = new MailDto();
        if(snakeCatcherRepository.findBySnakeCatcherIdAndStatus(snakeCatcher.getSnakeCatcherId(),statusValue.ACTIVE.sts()) == null){
            throw new ResourceNotFoundException("Snake Catcher Data Not Found!");
        }
        if(snakeCatcherRepository.save(snakeCatcher) != null){
            mailDto.setMessage("Your account details has been updated.!");
            mailDto.setSubject("Update Account Details");
            mailDto.setToMail(snakeCatcher.getEmail());
            emailService.sendMail(mailDto);

            commonResponse.setMessages(Arrays.asList("Updated!"));
            commonResponse.setPayload(true);
            commonResponse.setPayload(snakeCatcher);
        }
        return commonResponse;
    }

    public CommonResponse deleteSnakeCatcher(long snakeCatcherId){
        CommonResponse commonResponse = new CommonResponse();
        SnakeCatcher snakeCatcher = snakeCatcherRepository.findBySnakeCatcherIdAndStatus(snakeCatcherId, statusValue.ACTIVE.sts());
        Users user = new Users();

        if(snakeCatcher == null){
            throw new ResourceNotFoundException("Snake Cather Data Not Found!");
        }

        user = usersRepository.findByRegNoAndStatus(snakeCatcher.getRegNo(),statusValue.ACTIVE.sts());

        snakeCatcher.setStatus(statusValue.DEACTIVE.sts());
        user.setStatus(statusValue.DEACTIVE.sts());
        snakeCatcherRepository.save(snakeCatcher);
        usersRepository.save(user);

        commonResponse.setMessages(Arrays.asList("Done!"));
        commonResponse.setStatus(true);

        return commonResponse;
    }

    public CommonResponse getAllSnakeCatchers(){
        CommonResponse commonResponse = new CommonResponse();
        List<SnakeCatcher> snakeCatcherList = snakeCatcherRepository.findByStatus(statusValue.ACTIVE.sts());

        if(snakeCatcherList.size() == 0){
            throw new ResourceNotFoundException("No Any Snake Catchers Data.!");
        }

        commonResponse.setStatus(true);
        commonResponse.setPayload(snakeCatcherList);
        commonResponse.setMessages(Arrays.asList("Data Found.!"));

        return commonResponse;

    }

    public CommonResponse getSnakeCatherDataByRegNo(String regNo) {
        CommonResponse commonResponse = new CommonResponse();
        SnakeCatcher snakeCatcher = snakeCatcherRepository.findByRegNoAndStatus(regNo, statusValue.ACTIVE.sts());
        if(snakeCatcher == null){
            throw new ResourceNotFoundException("Data Not Found.!");
        }
        commonResponse.setPayload(snakeCatcher);
        commonResponse.setStatus(true);
        commonResponse.setMessages(Arrays.asList("Data Found.!"));

        return commonResponse;
    }
}
