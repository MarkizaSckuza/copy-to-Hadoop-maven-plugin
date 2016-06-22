package org.margo.plugins.copier;

import org.margo.plugins.copier.annotations.Reader;
import org.margo.plugins.copier.annotations.Writer;
import org.reflections.Reflections;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import static com.google.common.io.Resources.getResource;

public class Parser {

    private static Properties properties = new Properties();

   static {
        try {
            FileInputStream fileInputStream = new FileInputStream(getResource("properties.properties").getPath());
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Set<Class<?>> getDownloaders(){
        String[] downloaders = properties.getProperty("downloaders").split(",");
        Set<Class<?>> classes = new HashSet<>();
        Arrays.asList(downloaders).forEach(x -> classes.addAll(new Reflections(x).getTypesAnnotatedWith(Reader.class)));

        return classes;
    }

    public static Set<Class<?>> getUploaders() {
        String[] uploaders = properties.getProperty("uploaders").split(",");
        Set<Class<?>> classes = new HashSet<>();
        Arrays.asList(uploaders).forEach(x -> classes.addAll(new Reflections(x).getTypesAnnotatedWith(Writer.class)));

        return classes;
    }
}
