package com.bankofrhaen.appbankapplication.service.impl;

import com.bankofrhaen.appbankapplication.dto.BankResponse;
import com.bankofrhaen.appbankapplication.dto.CreditDebitDTO;
import com.bankofrhaen.appbankapplication.dto.EnquiryDTO;
import com.bankofrhaen.appbankapplication.dto.UserDTO;

public interface UserService {

    BankResponse createAccount(UserDTO userDTO);

    BankResponse balanceEnquiry(EnquiryDTO enquiryDTO);

    String nameEnquiry(EnquiryDTO enquiryDTO);

    BankResponse creditAccount(CreditDebitDTO creditDebitDTO);
}
