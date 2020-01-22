package org.pathref;

import org.apache.commons.beanutils.PropertyUtils;

public class PropertySetter {
    public static <T> T setProperty(String path, String value, Class<T> clazz) {
        T objectToInitialize;
        try {
            objectToInitialize = clazz.newInstance();
            PropertyUtils.setProperty(objectToInitialize, path, value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return objectToInitialize;
    }
}
