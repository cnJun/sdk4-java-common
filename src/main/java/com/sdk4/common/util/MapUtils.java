package com.sdk4.common.util;

import com.alibaba.fastjson.JSON;
import com.google.common.base.CaseFormat;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author sh
 */
public class MapUtils {
    private static Logger log = LoggerFactory.getLogger(MapUtils.class);

    /**
     * 判断是否为空.
     */
    public static boolean isEmpty(final Map<?, ?> map) {
        return (map == null) || map.isEmpty();
    }

    /**
     * 判断是否为空.
     */
    public static boolean isNotEmpty(final Map<?, ?> map) {
        return (map != null) && !map.isEmpty();
    }

    public static <T> T toJavaBeanObject(Map map, Class<T> cls)
            throws IntrospectionException, IllegalAccessException, InstantiationException {
        return toJavaBeanObject(map, cls, cls.newInstance());
    }

    public static <T> T toJavaBeanObject(Map map, Class<T> cls, T object)
            throws IntrospectionException, IllegalAccessException, InstantiationException {
        BeanInfo beanInfo = Introspector.getBeanInfo(cls);
        PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor prop : props) {
            String name = prop.getName();
            if (map.containsKey(name)) {
                Object value = map.get(name);

                // Map key 支持下划线连接的命名方式
                if (value == null) {
                    String name2 = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
                    value = map.get(name2);
                }

                if (value == null) {
                    continue;
                }

                // value值和类属性类型不一致时需要做类型转换
                Object value2 = null;
                Class type = prop.getPropertyType();
                try {
                    if (ClassUtils.isPrimitiveClass(type)) {
                        if (type == Date.class) {
                            value2 = DateUtils.parseDate(value.toString());
                        } else {
                            value2 = ConvertUtils.convert(value, type);
                        }
                    } else if (type.isEnum()) {
                        // Object[] objs = type.getEnumConstants();
                        String tmp = value.toString();
                        if (StringUtils.isNotBlank(tmp)) {
                            value2 = Enum.valueOf(type, value.toString());
                        }
                    } else if (type.getInterfaces() != null && type.getInterfaces().length > 0 && type.getInterfaces()[0] == Collection.class) {
                        // TODO: 集合
                        ParameterizedType pt = (ParameterizedType) prop.getReadMethod().getGenericReturnType();
                        Type[] actTypes = pt.getActualTypeArguments();
                        if (actTypes != null && actTypes.length > 0) {
                            Type actType = actTypes[0];

                            String valString = value.toString();
                            if (actType == String.class || !valString.startsWith("[")) {
                                String arr[] = valString.split("[,;]");
                                List<String> list = Arrays.asList(arr);
                                value2 = list;
                            } else {
                                value2 = JSON.parseArray(valString, (Class) actType);
                            }
                        }
                    } else if (type == Map.class) {
                        value2 = JSON.parseObject(value.toString(), Map.class);
                    } else {
                        // TODO: 自定义类型 ???
                    }
                    if (value2 != null) {
                        prop.getWriteMethod().invoke(object, value2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    log.warn("convert map to javabean, property value error ({})", name, e);
                }
            }
        }

        return object;
    }
}
