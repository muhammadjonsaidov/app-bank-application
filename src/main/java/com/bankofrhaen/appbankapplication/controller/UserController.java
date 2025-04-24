package com.bankofrhaen.appbankapplication.controller;

import com.bankofrhaen.appbankapplication.dto.BankResponse;
import com.bankofrhaen.appbankapplication.dto.CreditDebitDTO;
import com.bankofrhaen.appbankapplication.dto.EnquiryDTO;
import com.bankofrhaen.appbankapplication.dto.UserDTO;
import com.bankofrhaen.appbankapplication.service.impl.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public record UserController(UserService userService) {

    @PostMapping
    public BankResponse createAccount(@RequestBody UserDTO userDTO) {
        return userService.createAccount(userDTO);
    }

    @GetMapping("/balanceEnquiry")
    public BankResponse balanceEnquiry(@RequestBody EnquiryDTO enquiryDTO) {
        return userService.balanceEnquiry(enquiryDTO);
    }

    @GetMapping("/nameEnquiry")
    public String nameEnquiry(@RequestBody EnquiryDTO enquiryDTO) {
        return userService.nameEnquiry(enquiryDTO);
    }

    @PostMapping("/credit")
    public BankResponse creditAccount(@RequestBody CreditDebitDTO creditDebitDTO) {
        return userService.creditAccount(creditDebitDTO);
    }

    @PostMapping("/debit")
    public BankResponse debitAccount(@RequestBody CreditDebitDTO creditDebitDTO) {
        return userService.debitAccount(creditDebitDTO);
    }
}
