package org.example.blog.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.example.blog.dto.LoginRequest;
import org.example.blog.dto.LogoutRequest;
import org.example.blog.dto.RegisterRequest;
import org.example.blog.entity.User;
import org.example.blog.exception.ServiceException;
import org.example.blog.mapper.UserMapper;
import org.example.blog.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtUtil jwtUtil;

    public Map<String, Object> register(RegisterRequest req) {

        if (userMapper.findByPhone(req.getMobilePhoneNum()) != null) {
            throw new ServiceException(-20003, "user already exist");
        }

        User user = new User();
        user.setPhoneNumber(req.getMobilePhoneNum());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setUsername(req.getUserName() != null ? req.getUserName() : req.getMobilePhoneNum());
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insertUser(user);

        Map<String, Object> result = new HashMap<>();
        result.put("userid", user.getUserId());
        result.put("username", user.getUsername());
        return result;
    }

    public Map<String, Object> login(LoginRequest req) {

        User user = userMapper.findByPhone(req.getMobilePhoneNum());
        if (user == null || !passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new ServiceException(-20004, "user is not exist or password is incorrect");
        }
        String token = jwtUtil.generateToken(user.getPhoneNumber(), user.getUserId(),  user.getUsername());
        redisTemplate.opsForValue().set("TOKEN:" + user.getPhoneNumber(), token, 1, TimeUnit.HOURS);

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);

        return result;
    }

    public void logout(LogoutRequest req) {
        Jws<Claims> claims;
        try {
            claims = jwtUtil.parseToken(req.getToken());
        } catch (JwtException e) {
            throw new ServiceException(-20005, "token is invalid");
        }
        String storedToken = redisTemplate.opsForValue().get("TOKEN:" + req.getMobilePhoneNum());

        if (storedToken == null || !storedToken.equals(req.getToken()) ) {
            throw new ServiceException(-20006, "token is not right");
        }

        redisTemplate.delete("TOKEN:" + req.getMobilePhoneNum());
    }
}
