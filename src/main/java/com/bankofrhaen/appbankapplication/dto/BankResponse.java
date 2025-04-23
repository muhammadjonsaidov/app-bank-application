package com.bankofrhaen.appbankapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankResponse {

    private String code;

    private String message;

    private AccountInfo accountInfo;

}
