package com.bankofrhaen.appbankapplication.service.impl;

import com.bankofrhaen.appbankapplication.dto.BankResponse;
import com.bankofrhaen.appbankapplication.dto.UserDTO;

public interface UserService {

    BankResponse createAccount(UserDTO userDTO);
}
