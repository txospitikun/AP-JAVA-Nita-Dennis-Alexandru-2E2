package org.example;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ClassAnalyzer {
    public static void analyzeClass(Class<?> clazz) {
        System.out.println("Class Name: " + clazz.getName());

        Method[] methods = clazz.getDeclaredMethods();

        System.out.println("Methods:");
        for (Method method : methods) {
            System.out.println(Modifier.toString(method.getModifiers()) + " " + method.getReturnType().getName() + " " + method.getName() + "()");
        }
    }
}
