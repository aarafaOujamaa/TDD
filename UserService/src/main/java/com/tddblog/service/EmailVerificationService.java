package com.tddblog.service;

import com.tddblog.service.model.User;

public interface EmailVerificationService {

    public void sendEmailConfirmation(final User user);
}
