package colonialobfuscator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * from http://www.javafaq.nu/java-example-code-895.html
 */

public class ClassPathHacker {

    @SuppressWarnings("deprecation")
    public static void addFile(File f) throws IOException {
        if (f.isDirectory())
            for (File sub : f.listFiles())
                addFile(sub);
        else
            addURL(f.toURL());
    }

    @SuppressWarnings("unchecked")
    public static void addURL(URL u) throws IOException {

        URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class sysclass = URLClassLoader.class;

        try {
            Method method = sysclass.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(sysloader, u);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new IOException("Error, could not add URL to system classloader");
        }
    }
}
