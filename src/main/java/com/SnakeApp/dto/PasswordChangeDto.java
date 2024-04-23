package com.SnakeApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeDto {
    private String email;
    private String curPassword;
    private String newPassword;
}
