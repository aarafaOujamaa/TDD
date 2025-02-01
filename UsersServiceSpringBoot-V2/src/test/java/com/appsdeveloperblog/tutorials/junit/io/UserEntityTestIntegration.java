package com.appsdeveloperblog.tutorials.junit.io;


import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.http.ResponseEntity;

import java.util.UUID;
/*
    @DataJpaTest annotation in Spring Boot provides a convenient setup specifically for
    testing the persistence layer of an application. It configures an in-memory database,
    scans for @Entity classes, and configures Spring Data JPA repositories.
    It's particularly useful when you want to test JPA and Spring Data JPA components
    without loading other unrelated components.
 */
@DataJpaTest
public class UserEntityTestIntegration {

    @Autowired
    TestEntityManager testEntityManager;
    UserEntity userEntity;

    @BeforeEach
    public void init() {
        userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setFirstName("LINA");
        userEntity.setLastName("Ouja");
        userEntity.setEmail("lina.ou@yopmai.com");
        userEntity.setEncryptedPassword("1233455");
    }


    @Test
    public void testUserEntity_whenValidUserDetailsisProvided_shouldReturnStoredUserDetails() {
        // Arrange

        // Act
        UserEntity storedUser = testEntityManager.persistAndFlush(userEntity);

        // Assert
        Assertions.assertTrue(storedUser.getId() > 0);
        Assertions.assertEquals(userEntity.getEmail(), storedUser.getEmail());
        Assertions.assertEquals(userEntity.getFirstName(), storedUser.getFirstName());
        Assertions.assertEquals(userEntity.getLastName(), storedUser.getLastName());
        Assertions.assertEquals(userEntity.getEncryptedPassword(), storedUser.getEncryptedPassword());
    }

    @Test
    public void testUserEntity_whenExsitingUserIdProvided_shouldThrowException() {
        // Arrange
        // create and Persist a new User Entity
        UserEntity newUserEntity = new UserEntity();
            newUserEntity.setUserId("1");
            newUserEntity.setFirstName("test");
            newUserEntity.setLastName("test");
            newUserEntity.setEmail("test.ou@yopmai.com");
            newUserEntity.setEncryptedPassword("1233455");
        // Act
        testEntityManager.persistAndFlush(newUserEntity);

        // update exsiting 'userEntity' user with the same userId of 'newUserEntity' :PA->UserId is unique filed
        userEntity.setUserId("1");

        // Act && Assert
        Assertions.assertThrows(PersistenceException.class, () ->{
            testEntityManager.persistAndFlush(userEntity);
        }, "Was excepting a PersistenceException to be thrown");
    }


    @Test
    public void testUserEntity_whenFirstNameisTooLong_shloudhrowException() {
        // Arrange
        userEntity.setFirstName("LINAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        // Act && Assert
        Assertions.assertThrows(PersistenceException.class, ()->{
            testEntityManager.persistAndFlush(userEntity);
        }, "Was excepting a PersistenceException to be thrown");
    }
}
