package org.example;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java org.example.Main <path-to-class-or-folder-or-jar>");
            return;
        }

        String path = args[0];
        File file = new File(path);

        List<Class<?>> classes = new ArrayList<>();
        try {
            if (file.isDirectory()) {
                classes = loadClassesFromDirectory(file);
            } else if (file.getName().endsWith(".jar")) {
                classes = loadClassesFromJar(file);
            } else if (file.getName().endsWith(".class")) {
                classes.add(loadClassFromFile(file));
            }

            for (Class<?> clazz : classes) {
                ClassAnalyzer.analyzeClass(clazz);
                TestExecutor.executeTests(clazz);
            }

            TestExecutor.printTestStatistics();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Class<?>> loadClassesFromDirectory(File directory) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        Files.walk(Paths.get(directory.getAbsolutePath()))
                .filter(Files::isRegularFile)
                .filter(f -> f.toString().endsWith(".class"))
                .forEach(f -> {
                    try {
                        classes.add(loadClassFromFile(f.toFile(), directory));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        return classes;
    }

    private static List<Class<?>> loadClassesFromJar(File jarFile) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();
            URL[] urls = { new URL("jar:file:" + jarFile.getAbsolutePath() + "!/") };
            URLClassLoader classLoader = URLClassLoader.newInstance(urls);

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().endsWith(".class")) {
                    String className = entry.getName().replace('/', '.').replace(".class", "");
                    classes.add(classLoader.loadClass(className));
                }
            }
        }
        return classes;
    }

    private static Class<?> loadClassFromFile(File classFile) throws Exception {
        String className = getClassNameFromFile(classFile, classFile.getParentFile());
        return ClassLoaderUtil.loadClass(className, classFile.getParent());
    }

    private static Class<?> loadClassFromFile(File classFile, File baseDir) throws Exception {
        String className = getClassNameFromFile(classFile, baseDir);
        return ClassLoaderUtil.loadClass(className, baseDir.getAbsolutePath());
    }

    private static String getClassNameFromFile(File classFile, File baseDir) throws IOException {
        String filePath = classFile.getAbsolutePath();
        String basePath = baseDir.getAbsolutePath();
        if (filePath.startsWith(basePath)) {
            String className = filePath.substring(basePath.length() + 1)
                    .replace(File.separatorChar, '.')
                    .replace(".class", "");
            return className;
        } else {
            throw new IOException("Class file is not in the specified base directory");
        }
    }
}
