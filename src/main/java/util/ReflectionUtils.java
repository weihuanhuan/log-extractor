package util;

import java.lang.reflect.Field;

/**
 * Created by JasonFitch on 4/16/2020.
 */
public class ReflectionUtils {

    public static String getFieldValue(Object object, Field field) {
        try {
            field.setAccessible(true);
            Object value = field.get(object);
            return (String) value;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return Constants.EMPTY_STRING;
    }

    public static void setFieldValue(Object object, Field field, String value) {
        try {
            field.setAccessible(true);
            field.set(object, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
