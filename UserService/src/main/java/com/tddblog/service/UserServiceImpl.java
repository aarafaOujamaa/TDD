package com.tddblog.service;

import com.tddblog.exceptions.UserServiceException;
import com.tddblog.service.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.UUID;

public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    public UsersRepository usersRepository;
    public EmailVerificationService emailVerificationService;

    public UserServiceImpl(UsersRepository usersRepository, EmailVerificationService emailVerificationService) {
        this.usersRepository = usersRepository;
        this.emailVerificationService = emailVerificationService;
    }

    @Override
    public boolean createUser(String firstName, String lastName, String email, String password, String repeatPassword) {
        boolean isUserCreated;

        if(StringUtils.isEmpty(firstName)) {
            throw new IllegalArgumentException("FirstName is must not be null");
        }
        if(StringUtils.isEmpty(lastName)) {
            throw new IllegalArgumentException("lastName is must not be null");
        }

        User user = new User(firstName, lastName, email, password, repeatPassword, UUID.randomUUID().toString());

         try {
             isUserCreated = usersRepository.save(user);
           } catch (RuntimeException e) {
               throw new UserServiceException();
           }

          try {
            emailVerificationService.sendEmailConfirmation(user);
            } catch (RuntimeException e) {
                throw new UserServiceException();
            }
         return isUserCreated;
    }
}
