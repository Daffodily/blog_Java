package org.example.blog.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long userId;
    private String phoneNumber;
    private String password;
    private String username;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}