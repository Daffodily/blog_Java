package org.example.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LogoutRequest {
    @NotBlank(message = "PhoneNumInvalid")
    @Pattern(regexp = "^\\d{11}$", message = "PhoneNumInvalid")
    private String mobilePhoneNum;

    @NotBlank(message = "TokenInvalid")
    private String token;
}
