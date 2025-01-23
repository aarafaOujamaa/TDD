package com.tddblog.service;

import com.tddblog.exceptions.EmailNotificationException;
import com.tddblog.exceptions.UserServiceException;
import com.tddblog.service.model.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UsersRepository usersRepository;

    @Mock
    EmailVerificationService emailVerificationService;

    @InjectMocks
    UserServiceImpl userService;

    String firstName;
    String password;
    String lastName;
    String email;
    String repeatPassword;

    @BeforeEach
    public void init() {
        userService = new UserServiceImpl(usersRepository, emailVerificationService);
        firstName="Lina";
        email="ico@peaceday.com";
        password="1234";
        repeatPassword="1234";
        lastName="ouja";
    }

        // Life Cycle TDD : Red-> Green -> Refactor -> Repeat
        @Test
        public void testCreatedUser_WhenUserDetailProdivded_ThenReturnTrue() {
        // Arrang
        Mockito.when(usersRepository.save(Mockito.any(User.class))).thenReturn(true);
        // Act
        boolean isUserCreatd = userService.createUser(firstName, lastName, email, password, repeatPassword);
        //Assert
        Assertions.assertEquals(isUserCreatd, true, () -> "User Should be save in db");
        Mockito.verify(usersRepository, Mockito.times(1)).save(Mockito.any(User.class));
    }

    @Test
    public void TestCreatdUser_WhenSaveMethodThrowException_UserServiceThrowExceptionToo() {
         // Arrange
          Mockito.when(usersRepository.save(Mockito.any(User.class))).thenThrow(RuntimeException.class);
        // Act && Assert
        Assertions.assertThrows(UserServiceException.class, () -> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        });

        Mockito.verify(usersRepository).save(Mockito.any(User.class));

    }

    @Test
    public void TestCreatdUser_WhenSendEmailConfirmationThrowException_UserServiceThrowExceptionToo() {
        // Arrange
        // Method return value
        Mockito.when(usersRepository.save(Mockito.any(User.class))).thenReturn(true);
        // <! -- Test with void method : sendEmailConfirmation-->
        //Not Working : Mockito.when(emailVerificationService.sendEmailConfirmation(Mockito.any(User.class))).thenThrow(RuntimeException.class);
       Mockito.doThrow(EmailNotificationException.class)
               .when(emailVerificationService)
               .sendEmailConfirmation(Mockito.any(User.class));
       // Act
         Assertions.assertThrows(UserServiceException.class, () -> {
             userService.createUser(firstName, lastName, email, password, repeatPassword);
         }, "");
        // Assert
        Mockito.verify(usersRepository, Mockito.times(1)).save(Mockito.any(User.class));
    }

    @Test
    public void TestCreatdUser_WhenUserIsCreated_InvokeemailVerification() {
        // Arrange
        Mockito.when(usersRepository.save(Mockito.any(User.class))).thenReturn(true);
        Mockito.doNothing().when(emailVerificationService).sendEmailConfirmation(Mockito.any(User.class));
        // Act
         userService.createUser(firstName, lastName, email, password, repeatPassword);
        // Assert
        Mockito.verify(emailVerificationService, Mockito.times(1))
                .sendEmailConfirmation(Mockito.any(User.class));
    }

    @Test
    public void testCreatedUser_WhenFirstNameisMissing_ShouldThrowIlligalArgumentsException() {
        //AAA
        firstName="";
        // Act && Assert
        IllegalArgumentException missingFormatArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        });
        // Assert
        Assertions.assertEquals("FirstName is must not be null", missingFormatArgumentException.getMessage(), () -> "CreatedUser should return IllegalArgumentException With specified Message");
    }



}
