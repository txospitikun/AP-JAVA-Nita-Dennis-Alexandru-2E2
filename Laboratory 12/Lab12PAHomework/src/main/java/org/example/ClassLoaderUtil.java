package org.example;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class ClassLoaderUtil {
    public static Class<?> loadClass(String className, String classpath) throws Exception {
        File file = new File(classpath);
        URL url = file.toURI().toURL();
        URL[] urls = new URL[]{url};

        try (URLClassLoader loader = new URLClassLoader(urls)) {
            return loader.loadClass(className);
        }
    }
}
