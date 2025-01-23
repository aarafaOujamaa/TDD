package com.tddblog.repository;

import com.tddblog.service.UsersRepository;
import com.tddblog.service.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UsersRepositoryImpl implements UsersRepository {


    @Override
    public boolean save(User user ) {
        Map<String, User> usersDb = new HashMap<>();

        if(Objects.nonNull(user)) {
            usersDb.put(user.getFirstName(), user);
            return true;
        }
        return false;
    }

}
