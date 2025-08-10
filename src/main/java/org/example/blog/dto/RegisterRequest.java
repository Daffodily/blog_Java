package org.example.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "PhoneNumInvalid")
    @Pattern(regexp = "^\\d{11}$", message = "PhoneNumInvalid")
    private String mobilePhoneNum;

    @NotBlank(message = "PasswordInvalid")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,11}$", message = "PasswordInvalid")
    private String password;

    private String userName; // 可选
}
