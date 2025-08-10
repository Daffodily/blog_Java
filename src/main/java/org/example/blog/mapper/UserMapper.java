package org.example.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.blog.entity.User;

@Mapper
public interface UserMapper {
    User findByPhone(@Param("mobilePhoneNum") String phone);

    void insertUser(User user);
}