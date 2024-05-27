package org.example;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class TestExecutor {
    private static int totalTests = 0;
    private static int passedTests = 0;
    private static int failedTests = 0;

    public static void executeTests(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                totalTests++;
                try {
                    Object instance = null;
                    if (!Modifier.isStatic(method.getModifiers())) {
                        instance = clazz.getDeclaredConstructor().newInstance();
                    }
                    Object[] args = generateMockArguments(method);
                    method.setAccessible(true);
                    method.invoke(instance, args);
                    System.out.println("Test method " + method.getName() + " passed.");
                    passedTests++;
                } catch (InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchMethodException e) {
                    System.out.println("Test method " + method.getName() + " failed: " + e.getCause());
                    failedTests++;
                }
            }
        }
    }

    private static Object[] generateMockArguments(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] args = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            if (parameterTypes[i] == int.class) {
                args[i] = 0;
            } else if (parameterTypes[i] == String.class) {
                args[i] = "test";
            } else {
                args[i] = null; // You can add more mock values for different types if needed
            }
        }
        return args;
    }

    public static void printTestStatistics() {
        System.out.println("Total tests: " + totalTests);
        System.out.println("Passed tests: " + passedTests);
        System.out.println("Failed tests: " + failedTests);
    }
}
