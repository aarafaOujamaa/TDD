package com.tddblog.service;

import com.tddblog.service.model.User;

public class EmailVerificationServiceImpl implements EmailVerificationService {

    @Override
    public void sendEmailConfirmation(User user) {
          System.out.println("User " + user);
    }
}
