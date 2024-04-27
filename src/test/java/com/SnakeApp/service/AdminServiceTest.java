package com.SnakeApp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.SnakeApp.dto.AdminDto;
import com.SnakeApp.dto.MailDto;
import com.SnakeApp.entity.Admin;
import com.SnakeApp.entity.Users;
import com.SnakeApp.enums.statusValue;
import com.SnakeApp.enums.stakeHolderValues;
import com.SnakeApp.exeption.ResourceFoundException;
import com.SnakeApp.exeption.ResourceNotFoundException;
import com.SnakeApp.repository.AdminRepository;
import com.SnakeApp.repository.UsersRepository;
import com.SnakeApp.service.AdminService;
import com.SnakeApp.service.CommonService;
import com.SnakeApp.service.EmailService;
import com.SnakeApp.service.UsersService;
import com.SnakeApp.util.CommonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private CommonService commonService;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private UsersService usersService;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void testGetAllAdmin_WhenAdminListIsEmpty() {
        // Mock adminRepository to return an empty list
        when(adminRepository.findAllByStatus(statusValue.ACTIVE.sts())).thenReturn(Collections.emptyList());

        // Call getAllAdmin directly
        try {
            adminService.getAllAdmin();
            fail("Expected ResourceNotFoundException to be thrown, but nothing was thrown.");
        } catch (ResourceNotFoundException e) {
            assertEquals("Admin Data Not Found!", e.getMessage());
        }
    }




    @Test
    public void testGetAdminByRegNo_WhenAdminNotFound() {
        when(adminRepository.findByRegNoAndStatus("REGNO123", statusValue.ACTIVE.sts())).thenReturn(null);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> adminService.getAdminByRegNo("REGNO123"));
        assertEquals("Admin Data Not Found For : REGNO123", exception.getMessage());
    }

    @Test
    public void testUpdateAdmin_WhenAdminNotFound() {
        AdminDto adminDto = new AdminDto();
        adminDto.setRegNo("24100001");

        when(adminRepository.findByRegNoAndStatus("24100001", statusValue.ACTIVE.sts())).thenReturn(null);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> adminService.updateAdmin(adminDto));
        assertEquals("Admin Data Not Found!", exception.getMessage());
    }

    @Test
    public void testDeleteAdmin_WhenAdminNotFound() {
        when(adminRepository.findByAdminIdAndStatus(1L, statusValue.ACTIVE.sts())).thenReturn(null);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> adminService.deleteAdmin(1L));
        assertEquals("Admin Data Not Found!", exception.getMessage());
    }

//    @Test
//    public void testUpdateAdmin_WhenAdminIsUpdatedSuccessfully() {
//        AdminDto adminDto = new AdminDto();
//        adminDto.setRegNo("REGNO123");
//
//        Admin existingAdmin = new Admin();
//        existingAdmin.setRegNo("REGNO123");
//        when(adminRepository.findByRegNoAndStatus("REGNO123", statusValue.ACTIVE.sts())).thenReturn(existingAdmin);
//        when(adminRepository.save(any(Admin.class))).thenReturn(existingAdmin);
//
//        CommonResponse response = adminService.updateAdmin(adminDto);
//        assertTrue(response.isStatus());
//        assertEquals("Admin Data has Updated!", response.getMessages().get(0));
//        assertEquals(existingAdmin, response.getPayload());
//    }

    @Test
    public void testDeleteAdmin_WhenAdminIsDeletedSuccessfully() {
        Admin existingAdmin = new Admin();
        existingAdmin.setAdminId(1L);
        existingAdmin.setEmail("test@example.com");
        when(adminRepository.findByAdminIdAndStatus(1L, statusValue.ACTIVE.sts())).thenReturn(existingAdmin);
        when(adminRepository.save(any(Admin.class))).thenReturn(existingAdmin);

        CommonResponse response = adminService.deleteAdmin(1L);
        assertTrue(response.isStatus());
        assertEquals("Successfully Deleted!", response.getMessages().get(0));
        verify(usersService, times(1)).deleteUser("test@example.com");
    }
    @Test
    public void testUpdateAdmin_WhenAdminIsNotFound() {
        AdminDto adminDto = new AdminDto();
        adminDto.setRegNo("REGNO123");

        when(adminRepository.findByRegNoAndStatus("REGNO123", statusValue.ACTIVE.sts())).thenReturn(null);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> adminService.updateAdmin(adminDto));
        assertEquals("Admin Data Not Found!", exception.getMessage());
    }

    @Test
    public void testDeleteAdmin_WhenAdminIsNotFound() {
        when(adminRepository.findByAdminIdAndStatus(1L, statusValue.ACTIVE.sts())).thenReturn(null);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> adminService.deleteAdmin(1L));
        assertEquals("Admin Data Not Found!", exception.getMessage());
    }

    @Test
    public void testRun_WhenAdminRepositoryIsNotEmpty() throws Exception {
        when(adminRepository.count()).thenReturn(1L);

        adminService.run();

        verify(adminRepository, never()).save(any(Admin.class));
        verify(usersRepository, never()).save(any(Users.class));
        verify(emailService, never()).sendMail(any(MailDto.class));
    }

}


