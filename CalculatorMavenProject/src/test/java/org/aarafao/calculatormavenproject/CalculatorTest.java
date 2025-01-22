package org.aarafao.calculatormavenproject;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.util.StringUtils;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test methods of Calculator class")
public class CalculatorTest {

    Calculator calculator;



    @BeforeAll
    public static void  beforeAll() {
      System.out.println("Before All ");
    }

    @AfterAll
    public static void afterAll() {
      System.out.println("After All");
    }

    @BeforeEach
    public void beforeEach() {
        calculator = new Calculator();
    }

    @AfterEach
    public void afterEach() {
        System.out.println("---After Each Method----");
    }

    //How to give a name for our methodes : test<system under test>_<condition or State change>_<Expected Result>
    @DisplayName("test 10 / 2 = 5")
    @Test
    public void testIntegerateDivison_WhenTenDivedTwo_ShloudReturnTwo() {
        // Using (3A) -> AAA pattern
        // Arrange Given
        int expectedValue = 5;
        // Acte When
        int result = this.calculator.integerDivision(10, 2);
        // Assert  Then
       assertEquals(expectedValue, result, () -> result +  "is not equal to"+ expectedValue );
    }

   // @Disabled("TODO: Still need same works here to be tested!!")
    @DisplayName("Divison by Zero")
    @Test
    public void testIntegrationDivision_WhenTenDivedZero_ShouldThrowArithmeticException() {
       // AAA Pattern
       // Arrange
        int number1 = 10;
        int number2 = 0;
        String message = "/ by zero";
       // Assert && Act
       ArithmeticException arithmeticException = assertThrows(ArithmeticException.class, () -> {
            // ACT
            calculator.integerDivision(number1, number2);
        });
       // Assert
      assertEquals(message, arithmeticException.getMessage(), "is not the ArithmeticException as Expected");
    }


    @ParameterizedTest
    @ValueSource(strings = {"jhon","alsom","lina"})
    public void testGivenName(String firstName) {
        assertNotNull(firstName);
    }


    @ParameterizedTest
   //@MethodSource("sourceParams")
   /* @CsvSource({
            "20,12,8",
            "12,6,6"
    })*/
    @CsvFileSource(resources = "/susbtractionOperation.csv")
    public void  susbtractionOperation(int number1, int number2 , int expectedResult) {
        // Act
        int result = this.calculator.susbtrationOperation(number1, number2);
        // Assert
       assertEquals(expectedResult, result);
    }

    public static Stream<Arguments> sourceParams() {
        return  Stream.of( Arguments.of(10,2, 8),
                           Arguments.of(20,5, 15),
                           Arguments.of(30,20, 10)
        );
    }


}
