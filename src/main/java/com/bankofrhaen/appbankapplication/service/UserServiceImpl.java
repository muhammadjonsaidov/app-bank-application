package com.bankofrhaen.appbankapplication.service;

import com.bankofrhaen.appbankapplication.dto.*;
import com.bankofrhaen.appbankapplication.entity.User;
import com.bankofrhaen.appbankapplication.repository.UserRepository;
import com.bankofrhaen.appbankapplication.service.impl.EmailService;
import com.bankofrhaen.appbankapplication.service.impl.UserService;
import com.bankofrhaen.appbankapplication.util.AccountUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final EmailService emailService;

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

        // Send email alert to the user
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT CREATION")
                .msgBody("Dear " + savedUser.getFirstName() + ",\n\n" +
                        "Your account has been created successfully. Your account number is: " + savedUser.getAccountNumber() + "\n\n" +
                        "Thank you for choosing us.\n\n" +
                        "Best regards,\n" +
                        "Bank of Rhaen")
                .build();
        emailService.sendEmailAlert(emailDetails);

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

    @Override
    public BankResponse balanceEnquiry(EnquiryDTO enquiryDTO) {
        // Check if the account number exists in the database
        boolean isAccountExists = userRepository.existsByAccountNumber(enquiryDTO.getAccountNumber());

        if (!isAccountExists) {
            return BankResponse.builder()
                    .code(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .message(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User foundUser = userRepository.findByAccountNumber(enquiryDTO.getAccountNumber());
        return BankResponse.builder()
                .code(AccountUtils.ACCOUNT_FOUND_CODE)
                .message(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(foundUser.getAccountBalance())
                        .accountNumber(foundUser.getAccountNumber())
                        .accountName(foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName())
                        .build())
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryDTO enquiryDTO) {
        boolean isAccountExists = userRepository.existsByAccountNumber(enquiryDTO.getAccountNumber());

        if (!isAccountExists) {
            return AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE;
        }

        User foundUser = userRepository.findByAccountNumber(enquiryDTO.getAccountNumber());
        return foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName();
    }

    @Override
    public BankResponse creditAccount(CreditDebitDTO creditDebitDTO) {
        // checking if an account exists
        boolean isAccountExists = userRepository.existsByAccountNumber(creditDebitDTO.getAccountNumber());
        if (!isAccountExists) {
            return BankResponse.builder()
                    .code(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .message(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User userToCredit = userRepository.findByAccountNumber(creditDebitDTO.getAccountNumber());
        userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(creditDebitDTO.getAmount()));
        userRepository.save(userToCredit);

        return BankResponse.builder()
                .code(AccountUtils.ACCOUNT_CREDITED_SUCCESS)
                .message(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(userToCredit.getFirstName() + " " + userToCredit.getLastName() + " " + userToCredit.getOtherName())
                        .accountBalance(userToCredit.getAccountBalance())
                        .accountNumber(userToCredit.getAccountNumber())
                        .build())
                .build();
    }

    @Override
    public BankResponse debitAccount(CreditDebitDTO creditDebitDTO) {
        boolean isAccountExists = userRepository.existsByAccountNumber(creditDebitDTO.getAccountNumber());
        if (!isAccountExists) {
            return BankResponse.builder()
                    .code(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .message(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User userToDebit = userRepository.findByAccountNumber(creditDebitDTO.getAccountNumber());
        BigDecimal availableBalance = userToDebit.getAccountBalance();
        BigDecimal debitAmount = creditDebitDTO.getAmount();

        if (availableBalance.intValue() < debitAmount.intValue()) {
            return BankResponse.builder()
                    .code(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .message(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(creditDebitDTO.getAmount()));
        userRepository.save(userToDebit);

        return BankResponse.builder()
                .code(AccountUtils.ACCOUNT_DEBITED_SUCCESS)
                .message(AccountUtils.ACCOUNT_DEBITED_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(userToDebit.getFirstName() + " " + userToDebit.getLastName() + " " + userToDebit.getOtherName())
                        .accountBalance(userToDebit.getAccountBalance())
                        .accountNumber(userToDebit.getAccountNumber())
                        .build())
                .build();
    }
}
