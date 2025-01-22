package org.aarafao.calculatormavenproject;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RepatedTestDemo {

    Calculator calculator ;

    @BeforeEach
    public void beforeEach() {
        calculator = new Calculator();
    }

    @DisplayName("Test for divison By Zero")
    @RepeatedTest(3)
    //@Test
    // @ParameterizedTest
    // @MethodSource("dataSource") // testIntegerDivision_WhenDiviedByZero_ShouldThrowAritmeticException(int number1, int number2)
    //@ValueSource(ints = {10,15,100}) : testIntegerDivision_WhenDiviedByZero_ShouldThrowAritmeticException(int number1)
    // @CsvSource(strings = {"100","0"}) :testIntegerDivision_WhenDiviedByZero_ShouldThrowAritmeticException(int number1)
    public void testIntegerDivision_WhenDiviedByZero_ShouldThrowAritmeticException() {
       int number1 = 10;
       int number2 = 0;
       ArithmeticException  arithmeticException = assertThrows(ArithmeticException.class, () -> {
            calculator.integerDivision(number1, number2);
        });
         System.out.println(arithmeticException.getMessage());
         assertEquals("/ by zero", arithmeticException.getMessage(), ()->"is not the Expected Exception!");
    }

    public static Stream<Arguments> dataSource() {
        return Stream.of(
                Arguments.of(12,0),
                Arguments.of(12,0));
    }


}
