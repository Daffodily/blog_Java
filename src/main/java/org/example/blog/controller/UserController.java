package org.example.blog.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.blog.dto.LoginRequest;
import org.example.blog.dto.LogoutRequest;
import org.example.blog.dto.RegisterRequest;
import org.example.blog.dto.Response;
import org.example.blog.exception.ServiceException;
import org.example.blog.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public Map<String, Object> register(@Valid @RequestBody RegisterRequest req) {
        return Response.success(userService.register(req));
    }

    @PostMapping("/login")
    public Map<String, Object> login(@Valid @RequestBody LoginRequest req) {
        return Response.success(userService.login(req));

    }

    @PostMapping("/logout")
    public Map<String, Object> logout(@Valid @RequestBody LogoutRequest req) {
        Map<String, Object> res = new HashMap<>();
        userService.logout(req);
        res.put("code", 0);
        return res;

    }
}
