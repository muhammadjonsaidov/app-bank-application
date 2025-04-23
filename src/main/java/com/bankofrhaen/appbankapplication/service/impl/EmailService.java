package com.bankofrhaen.appbankapplication.service.impl;

import com.bankofrhaen.appbankapplication.dto.EmailDetails;

public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);
}
