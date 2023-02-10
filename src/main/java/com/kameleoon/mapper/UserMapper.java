package com.kameleoon.mapper;

import com.kameleoon.dto.user.UserInDTO;
import com.kameleoon.dto.user.UserOutDTO;
import com.kameleoon.entity.user.User;

public class UserMapper {

    public static UserOutDTO userToDto(User user) {
        return UserOutDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User dtoToUser(UserInDTO userInDto) {
        return User.builder()
                .name(userInDto.getName())
                .email(userInDto.getEmail())
                .build();
    }
}
