package com.bankofrhaen.appbankapplication.service;

import com.bankofrhaen.appbankapplication.dto.AccountInfo;
import com.bankofrhaen.appbankapplication.dto.BankResponse;
import com.bankofrhaen.appbankapplication.dto.UserDTO;
import com.bankofrhaen.appbankapplication.entity.User;
import com.bankofrhaen.appbankapplication.repository.UserRepository;
import com.bankofrhaen.appbankapplication.service.impl.UserService;
import com.bankofrhaen.appbankapplication.util.AccountUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public BankResponse createAccount(UserDTO userDTO) {
        /**
         * Creating a new account - saving a new user into the database
         * checking if the user already has an account
         */

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return BankResponse.builder()
                    .code(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .message(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User newUser = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .otherName(userDTO.getOtherName())
                .gender(userDTO.getGender())
                .address(userDTO.getAddress())
                .stateOfOrigin(userDTO.getStateOfOrigin())
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .email(userDTO.getEmail())
                .phoneNumber(userDTO.getPhoneNumber())
                .alternatePhoneNumber(userDTO.getAlternatePhoneNumber())
                .status("ACTIVE")
                .build();


        User savedUser = userRepository.save(newUser);
        return BankResponse.builder()
                .code(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .message(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(savedUser.getAccountBalance())
                        .accountNumber(savedUser.getAccountNumber())
                        .accountName(savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName())
                        .build())
                .build();
    }
}
