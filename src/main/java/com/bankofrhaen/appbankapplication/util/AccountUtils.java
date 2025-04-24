package com.bankofrhaen.appbankapplication.util;

import jakarta.servlet.http.PushBuilder;

import java.time.Year;

public class AccountUtils {

    public static final String  ACCOUNT_EXISTS_CODE = "001";
    public static final String  ACCOUNT_EXISTS_MESSAGE = "This user already has an account created!";

    public static final String  ACCOUNT_CREATION_SUCCESS = "002";
    public static final String  ACCOUNT_CREATION_MESSAGE = "Account created successfully!";

    public static final String  ACCOUNT_NOT_EXIST_CODE = "003";
    public static final String  ACCOUNT_NOT_EXIST_MESSAGE = "User account does not exist!";

    public static final String  ACCOUNT_FOUND_CODE = "004";
    public static final String  ACCOUNT_FOUND_MESSAGE = "User account found!";

    public static final String  ACCOUNT_CREDITED_SUCCESS = "005";
    public static final String  ACCOUNT_CREDITED_SUCCESS_MESSAGE = "Account credited successfully!";


    /**
     * 2025 + randomSixDigits
     */
    public static String generateAccountNumber() {
        Year currentYear = Year.now();

        int min = 100000;
        int max = 999999;

        // Generate a random number between 100000 and 999999
        int randomNumber = (int) (Math.random() * (max - min + 1) + min);

        // Convert the current and random number to a string, then concatenate them
        return String.valueOf(currentYear) + randomNumber;
    }
}
