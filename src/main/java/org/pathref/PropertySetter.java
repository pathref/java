package org.pathref;

import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class PropertySetter {
    public static <T> T setProperty(Class<T> clazz, String path, String value) {
        T objectToInitialize;
        try {
            objectToInitialize = clazz.newInstance();
            setPathProperty(objectToInitialize, path, value);
            setProperty(objectToInitialize, path, value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return objectToInitialize;
    }

    private static <T> void setPathProperty(T objectToInitialize, String path, String value) {
        final List<String> tokenizedPaths = tokenizePath(path);
        IntStream.range(0, tokenizedPaths.size())
                .forEach(index -> {
                            String tokenizedPath = tokenizedPaths.get(index);
                            if (index < tokenizedPaths.size() - 1) {
                                initializePathProperty(objectToInitialize, tokenizedPath);
                            }
                        }
                );
    }

    private static <T> void setProperty(T objectToInitialize, String path, String value) {
        try {
            PropertyUtils.setNestedProperty(objectToInitialize, path, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static <T> void initializePathProperty(T objectToInitialize, String propertyToInit) {
        Object initializedObject = null;
        try {
            Field field = objectToInitialize.getClass().getDeclaredField(propertyToInit);
            field.setAccessible(true);
            initializedObject = field.getType().newInstance();
            field.set(objectToInitialize, initializedObject);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> tokenizePath(String path) {
        return Arrays.asList(path.split("\\."));
    }
}
