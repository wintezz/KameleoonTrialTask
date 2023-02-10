package com.kameleoon.controller;

import com.kameleoon.dto.user.UserInDTO;
import com.kameleoon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public void create(@Valid
            @RequestBody UserInDTO userDTO) {

        userService.create(userDTO);
    }
}

