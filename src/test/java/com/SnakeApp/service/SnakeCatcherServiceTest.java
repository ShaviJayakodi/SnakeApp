package com.SnakeApp.service;

import com.SnakeApp.dto.MailDto;
import com.SnakeApp.dto.SnakeCatcherDto;
import com.SnakeApp.entity.SnakeCatcher;
import com.SnakeApp.entity.Users;
import com.SnakeApp.enums.statusValue;
import com.SnakeApp.exeption.ResourceFoundException;
import com.SnakeApp.exeption.ResourceNotFoundException;
import com.SnakeApp.repository.SnakeCatcherRepository;
import com.SnakeApp.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SnakeCatcherServiceTest {

    @Mock
    private CommonService commonService;

    @Mock
    private SnakeCatcherRepository snakeCatcherRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private SnakeCatcherService snakeCatcherService;

    @Test
    public void testMakingAnRequestToRegister_WithExistingEmail_ShouldThrowException() {
        // Arrange
        SnakeCatcherDto snakeCatcherDto = new SnakeCatcherDto();
        snakeCatcherDto.setEmail("test@example.com");

        when(snakeCatcherRepository.findByEmailAndStatus(eq("test@example.com"), eq(statusValue.ACTIVE.sts()))).thenReturn(new SnakeCatcher());

        // Act & Assert
        assertThrows(ResourceFoundException.class, () -> snakeCatcherService.makingAnRequestToRegister(snakeCatcherDto));
    }

    @Test
    public void testMakingAnRequestToRegister_WithPendingEmail_ShouldThrowException() {
        // Arrange
        SnakeCatcherDto snakeCatcherDto = new SnakeCatcherDto();
        snakeCatcherDto.setEmail("test@example.com");

        when(snakeCatcherRepository.findByEmailAndStatus(eq("test@example.com"), eq(statusValue.ACTIVE.sts()))).thenReturn(null);
        when(snakeCatcherRepository.findByEmailAndStatus(eq("test@example.com"), eq(statusValue.PENDING.sts()))).thenReturn(new SnakeCatcher());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> snakeCatcherService.makingAnRequestToRegister(snakeCatcherDto));
    }

    @Test
    public void testMakingAnRequestToRegister_WithNewEmail_ShouldNotThrowException() {
        // Arrange
        SnakeCatcherDto snakeCatcherDto = new SnakeCatcherDto();
        snakeCatcherDto.setEmail("new@example.com");
        snakeCatcherDto.setSnakeCatcherId(123);
        snakeCatcherDto.setDob(LocalDate.of(1990, 5, 15));

        when(snakeCatcherRepository.findByEmailAndStatus(eq("new@example.com"), eq(statusValue.ACTIVE.sts()))).thenReturn(null);
        when(snakeCatcherRepository.findByEmailAndStatus(eq("new@example.com"), eq(statusValue.PENDING.sts()))).thenReturn(null);

        when(commonService.generateRegNo(eq(123), eq("CODE"))).thenReturn("REG123");
        when(commonService.calculateAge(eq(snakeCatcherDto.getDob()))).thenReturn(30);

        SnakeCatcher savedSnakeCatcher = new SnakeCatcher();
        savedSnakeCatcher.setRegNo("REG123");
        savedSnakeCatcher.setAge(30);
        when(modelMapper.map(eq(snakeCatcherDto), eq(SnakeCatcher.class))).thenReturn(savedSnakeCatcher);

        when(snakeCatcherRepository.save(any())).thenReturn(new SnakeCatcher());

        // Act & Assert
        assertDoesNotThrow(() -> snakeCatcherService.makingAnRequestToRegister(snakeCatcherDto));
    }


    @Test
    public void testGetPendingApprovals_WithNoPendingRequests_ShouldThrowException() {
        // Arrange
        when(snakeCatcherRepository.findByStatus(eq(statusValue.PENDING.sts()))).thenReturn(Arrays.asList());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> snakeCatcherService.getPendingApprovals());
    }

    @Test
    public void testGetPendingApprovals_WithPendingRequests_ShouldNotThrowException() {
        // Arrange
        SnakeCatcher pendingSnakeCatcher = new SnakeCatcher();
        pendingSnakeCatcher.setSnakeCatcherId(1);
        when(snakeCatcherRepository.findByStatus(eq(statusValue.PENDING.sts()))).thenReturn(Arrays.asList(pendingSnakeCatcher));

        // Act & Assert
        assertDoesNotThrow(() -> snakeCatcherService.getPendingApprovals());
    }

    @Test
    public void testApprove_WithExistingSnakeCatcherAndStatus1_ShouldApprove() {
        // Arrange
        long snakeCatcherId = 1;
        int status = 1;
        SnakeCatcher snakeCatcher = new SnakeCatcher();
        snakeCatcher.setEmail("test@example.com");
        snakeCatcher.setStatus(statusValue.PENDING.sts());
        Users user = new Users();
        user.setEnPassword("testpassword");
        when(snakeCatcherRepository.findBySnakeCatcherIdAndStatus(eq(snakeCatcherId), eq(statusValue.PENDING.sts()))).thenReturn(snakeCatcher);
        when(usersRepository.findByRegNo(eq(snakeCatcher.getRegNo()))).thenReturn(user);

        // Act
        snakeCatcherService.approve(snakeCatcherId, status);

        // Assert
        assertEquals(statusValue.ACTIVE.sts(), snakeCatcher.getStatus());
        assertEquals(statusValue.ACTIVE.sts(), user.getStatus());
    }

    @Test
    public void testApprove_WithExistingSnakeCatcherAndStatus0_ShouldReject() {
        // Arrange
        long snakeCatcherId = 1;
        int status = 0;
        SnakeCatcher snakeCatcher = new SnakeCatcher();
        snakeCatcher.setEmail("test@example.com");
        snakeCatcher.setStatus(statusValue.PENDING.sts());
        Users user = new Users();
        user.setEnPassword("testpassword");
        when(snakeCatcherRepository.findBySnakeCatcherIdAndStatus(eq(snakeCatcherId), eq(statusValue.PENDING.sts()))).thenReturn(snakeCatcher);
        when(usersRepository.findByRegNo(eq(snakeCatcher.getRegNo()))).thenReturn(user);

        // Act
        snakeCatcherService.approve(snakeCatcherId, status);

        // Assert
        assertEquals(statusValue.DEACTIVE.sts(), snakeCatcher.getStatus());
        assertEquals(statusValue.DEACTIVE.sts(), user.getStatus());
    }

    @Test
    public void testDeleteSnakeCatcher_WithExistingSnakeCatcherAndStatusActive_ShouldDelete() {
        // Arrange
        long snakeCatcherId = 1;
        SnakeCatcher snakeCatcher = new SnakeCatcher();
        snakeCatcher.setStatus(statusValue.ACTIVE.sts());
        Users user = new Users();
        user.setStatus(statusValue.ACTIVE.sts());
        when(snakeCatcherRepository.findBySnakeCatcherIdAndStatus(eq(snakeCatcherId), eq(statusValue.ACTIVE.sts()))).thenReturn(snakeCatcher);
        when(usersRepository.findByRegNoAndStatus(eq(snakeCatcher.getRegNo()), eq(statusValue.ACTIVE.sts()))).thenReturn(user);

        // Act & Assert
        assertDoesNotThrow(() -> snakeCatcherService.deleteSnakeCatcher(snakeCatcherId));
    }

    @Test
    public void testDeleteSnakeCatcher_WithNonExistingSnakeCatcherAndStatusActive_ShouldThrowException() {
        // Arrange
        long snakeCatcherId = 1;
        when(snakeCatcherRepository.findBySnakeCatcherIdAndStatus(eq(snakeCatcherId), eq(statusValue.ACTIVE.sts()))).thenReturn(null);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> snakeCatcherService.deleteSnakeCatcher(snakeCatcherId));
    }

    @Test
    public void testGetAllSnakeCatchers_WithNoSnakeCatchers_ShouldThrowException() {
        // Arrange
        when(snakeCatcherRepository.findByStatus(eq(statusValue.ACTIVE.sts()))).thenReturn(Arrays.asList());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> snakeCatcherService.getAllSnakeCatchers());
    }

    @Test
    public void testGetAllSnakeCatchers_WithSnakeCatchers_ShouldNotThrowException() {
        // Arrange
        SnakeCatcher activeSnakeCatcher = new SnakeCatcher();
        activeSnakeCatcher.setSnakeCatcherId(1);
        when(snakeCatcherRepository.findByStatus(eq(statusValue.ACTIVE.sts()))).thenReturn(Arrays.asList(activeSnakeCatcher));

        // Act & Assert
        assertDoesNotThrow(() -> snakeCatcherService.getAllSnakeCatchers());
    }

    @Test
    public void testGetSnakeCatherDataByRegNo_WithExistingRegNo_ShouldNotThrowException() {
        // Arrange
        String regNo = "REG123";
        SnakeCatcher snakeCatcher = new SnakeCatcher();
        snakeCatcher.setSnakeCatcherId(1);
        when(snakeCatcherRepository.findByRegNoAndStatus(eq(regNo), eq(statusValue.ACTIVE.sts()))).thenReturn(snakeCatcher);

        // Act & Assert
        assertDoesNotThrow(() -> snakeCatcherService.getSnakeCatherDataByRegNo(regNo));
    }

    @Test
    public void testGetSnakeCatherDataByRegNo_WithNonExistingRegNo_ShouldThrowException() {
        // Arrange
        String regNo = "NONEXISTENT";
        when(snakeCatcherRepository.findByRegNoAndStatus(eq(regNo), eq(statusValue.ACTIVE.sts()))).thenReturn(null);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> snakeCatcherService.getSnakeCatherDataByRegNo(regNo));
    }

    // Add more test cases for other methods...

}
