package com.tddblog.service.model;

public class User {

     String firstName;
     String lastName;
     String email;
     String id;
     String password;
     String repeatPassword;

     public User(final String firstName, final String lastName, final String email, final String password, final String  repeatPassword, final String id) {
         this.firstName = firstName;
         this.lastName = lastName;
         this.email = email;
         this.id = id;
         this.password = password;
         this.repeatPassword = repeatPassword;
     }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }
}
