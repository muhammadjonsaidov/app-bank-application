package com.bankofrhaen.appbankapplication.controller;

import com.bankofrhaen.appbankapplication.dto.BankResponse;
import com.bankofrhaen.appbankapplication.dto.UserDTO;
import com.bankofrhaen.appbankapplication.service.impl.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public record UserController(UserService userService) {

    @PostMapping
    public BankResponse createAccount(@RequestBody UserDTO userDTO) {
        return userService.createAccount(userDTO);
    }
}
