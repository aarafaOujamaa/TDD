package org.aarafao.calculatormavenproject;


import org.aarafao.calculatormavenproject.db.UsersDatabase;
import org.aarafao.calculatormavenproject.db.UsersDatabaseMapImpl;
import org.aarafao.calculatormavenproject.services.UserService;
import org.aarafao.calculatormavenproject.services.UserServiceImpl;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceImplTest {
    UserService userSerivce;
    UsersDatabase usersDatabase;
    String createdUserId = "";

    @BeforeAll
    public void initUserServiceAndUsersDb() {
        usersDatabase = new UsersDatabaseMapImpl();
        usersDatabase.init();
        userSerivce = new UserServiceImpl(usersDatabase);
    }

    @DisplayName("Test Method of userSerivce:createUser ")
    @Order(1)
    @Test
    public void testcreateUser() {
        // Arrange
        Map<String, String> userDetails = new HashMap();
        userDetails.put("firstName", "Lina");
        userDetails.put("LastName", "Azou");

        // Act
         createdUserId = userSerivce.createUser(userDetails);

        // Assert
        assertNotNull(createdUserId);
    }

    @DisplayName("Test Method of userSerivce:UpdateUser ")
    @Order(2)
    @Test
    public void testupdateUser_WhenUpdatingFirstName_ShouldReturnSameUserId() {
        // Arrang
        Map userDetails = new HashMap();
        userDetails.put("firstName", "Iza");
        // Act
        Map userDetailsUpdated = userSerivce.updateUser(createdUserId, userDetails);

        // Assert
        assertEquals(userDetailsUpdated.get("firstName"), userDetails.get("firstName"));
        assertEquals(userDetailsUpdated.get("lastName"), userDetails.get("lastName"));
    }

    @DisplayName("Test Method of userSerivce:UpdateUser")
    @Order(3)
    @Test
    public void testUserDetailsMethod_WhenSearchUserById_ShouldReturnUserDetails() {
        // Act
        Map userDetailsUpdated = userSerivce.getUserDetails(createdUserId);
        // Assert
        assertNotNull(userDetailsUpdated, "UserDetail shloud not be Null");
        assertEquals(userDetailsUpdated.get("userId"), createdUserId);

    }


    @DisplayName("Test Method of userSerivce:deleteUser")
    @Order(3)
    @Test  // test<NomMethod>_Condition_Result()
    public void testDeleteUserMethod_WhenSearchUserById_ShouldReturnUserDetails() {
        // Act
        userSerivce.deleteUser(createdUserId);
        Map userDetailsUpdated = userSerivce.getUserDetails(createdUserId);
        // Assert
        assertNull(userDetailsUpdated, "UserDetail shloud  be Null");
    }
}
