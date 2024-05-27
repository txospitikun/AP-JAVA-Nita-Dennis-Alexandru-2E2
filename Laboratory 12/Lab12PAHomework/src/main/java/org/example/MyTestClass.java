package org.example;

public class MyTestClass {

    @Test
    public static void testMethod1() {
        System.out.println("Test method 1 executed!");
    }

    @Test
    public static void testMethod2() {
        System.out.println("Test method 2 executed!");
    }

    public static void nonTestMethod() {
        System.out.println("This method should not be executed as a test.");
    }

    @Test
    public static void anotherTestMethod() {
        System.out.println("Another test method executed!");
    }
}
