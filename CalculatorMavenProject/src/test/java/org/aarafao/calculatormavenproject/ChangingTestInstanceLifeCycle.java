package org.aarafao.calculatormavenproject;

import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // share the same instance of class instead of PER_METHOD (each instance for each method)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ChangingTestInstanceLifeCycle {

   StringBuilder str = new StringBuilder();

   @AfterEach
   public void afterAll() {
       System.out.println("str " + str);
   }

   @Test
    public void testA() {
       str.append("A");
   }

    @Test
    public void testB() {
        str.append("B");
    }

    @Test
    public void testC() {
        str.append("C");
    }


}
