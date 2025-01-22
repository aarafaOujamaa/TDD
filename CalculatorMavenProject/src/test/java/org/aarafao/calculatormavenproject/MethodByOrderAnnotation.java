package org.aarafao.calculatormavenproject;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

//@TestMethodOrder(MethodOrderer.MethodName.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MethodByOrderAnnotation {

    @Order(1)
    @Test
    public void TestB() {
        System.out.println("Test A");
    }

    @Order(2)
    @Test
    public void TestA() {
        System.out.println("Test A");
    }

    @Order(3)
    @Test
    public void TestC() {
        System.out.println("Test A");
    }


}
