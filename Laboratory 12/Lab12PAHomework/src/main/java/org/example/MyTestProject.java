package org.example;

public class MyTestProject {

    @Test
    public static void staticTestMethod() {
        System.out.println("Static test method executed!");
    }

    @Test
    public void instanceTestMethod() {
        System.out.println("Instance test method executed!");
    }

    @Test
    public void testWithArguments(int a, String b) {
        System.out.println("Test method with arguments executed: " + a + ", " + b);
    }

    public void nonTestMethod() {
        System.out.println("This is not a test method.");
    }
}
