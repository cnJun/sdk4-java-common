package com.sdk4.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sh
 */
public class ClassUtils {
    private final static List<Class<?>> _PrimitiveClasses = new ArrayList<Class<?>>() {
        {
            add(Boolean.class);
            add(Character.class);
            add(Byte.class);
            add(Long.class);
            add(Short.class);
            add(Double.class);
            add(Float.class);
            add(Integer.class);
            add(String.class);
            add(Void.class);
            add(java.util.Date.class);
            add(java.sql.Date.class);
            add(java.sql.Timestamp.class);
        }
    };

    public final static boolean isPrimitiveClass(Class<?> cls) {
        return cls.isPrimitive() || _PrimitiveClasses.contains(cls);
    }
}
