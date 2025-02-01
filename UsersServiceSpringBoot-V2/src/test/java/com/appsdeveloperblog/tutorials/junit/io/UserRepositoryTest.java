package com.appsdeveloperblog.tutorials.junit.io;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.UUID;

// -> The purpose here is to test our User JpaRepository
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private  UsersRepository usersRepository;

    private final String userId1 = UUID.randomUUID().toString();
    private final String userId2 = UUID.randomUUID().toString();
    private final String email1 = "test@test.com";
    private final String email2 = "test2@test.com";

    @BeforeEach
    void setup() {
        // Creating first user
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId1);
        userEntity.setEmail(email1);
        userEntity.setFirstName("test");
        userEntity.setLastName("test");
        userEntity.setEncryptedPassword("1233455");

        testEntityManager.persistAndFlush(userEntity);

        // Creating second user
        UserEntity userEntity2 = new UserEntity();
        userEntity2.setUserId(userId2);
        userEntity2.setEmail(email2);
        userEntity2.setEncryptedPassword("abcdefg1");
        userEntity2.setFirstName("John");
        userEntity2.setLastName("Sears");
        testEntityManager.persistAndFlush(userEntity2);
    }

    @Test
    void testFindByEmail_whenGivenCorrectEmail_returnsUserEntity() {
        // Act
        UserEntity storedUser = usersRepository.findByEmail(email1);

        // Assert
        Assertions.assertEquals(email1, storedUser.getEmail(),
                "The returned email address does not match the expected value");
    }

    @Test
    void testFindByUserId_whenGivenCorrectUserId_returnsUserEntity() {
        // Act
        UserEntity storedUser = usersRepository.findByUserId(userId2);

        // Assert
        Assertions.assertNotNull(storedUser,
                "UserEntity object should not be null");
        Assertions.assertEquals(userId2, storedUser.getUserId(),
                "Returned userId does not much expected value");
    }

    @Test
    void testFindByUserId_whenGivenIncorrectUserId_returnsNull() {
        // Arrange
        UserEntity userEntity = new UserEntity();
            userEntity.setUserId(UUID.randomUUID().toString());
            userEntity.setEmail("test@gmail.com");
            userEntity.setFirstName("QueryUser");
            userEntity.setLastName("Query");
            userEntity.setEncryptedPassword("1233455");
            testEntityManager.persistAndFlush(userEntity);
            String emailDomaineName = "@gmail.com";

        // Act
        List<UserEntity> users = usersRepository.findUsersWithEmailEndingWith(emailDomaineName);
        // Asser
        assertEquals(1, users.size(),
                "Expected 1 user to be returned, but found " + users.size());
        assertTrue(users.get(0).getEmail().endsWith(emailDomaineName));


    }


}
