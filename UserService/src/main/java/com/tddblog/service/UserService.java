package com.tddblog.service;


public interface UserService   {


     boolean createUser(final String firstName,
                           final String lastName,
                           final String email,
                           final String password,
                           final String repeatPassword);
}
