package com.kameleoon.service;

import com.kameleoon.dto.user.UserInDTO;
import com.kameleoon.dto.user.UserOutDTO;
import com.kameleoon.exception.ConflictException;
import com.kameleoon.mapper.UserMapper;
import com.kameleoon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.kameleoon.exception.ExceptionDescriptions.USER_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserOutDTO create(UserInDTO userInDto) {
        try {
            return UserMapper.userToDto(userRepository.save(UserMapper.dtoToUser(userInDto)));
        } catch (DataAccessException dataAccessException) {
            throw new ConflictException(USER_ALREADY_EXISTS.getTitle());
        }
    }
}
