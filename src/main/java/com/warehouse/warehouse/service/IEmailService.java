package com.warehouse.warehouse.service;

import com.warehouse.warehouse.model.VerificationToken;
import com.warehouse.warehouse.utility.exception.EmailFailureException;

public interface IEmailService {
    void sendVerificationEmail(VerificationToken verificationToken) throws EmailFailureException;
}
