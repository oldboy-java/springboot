package com.liuli.hash.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public final class MapUtils {

    public static Map<String, Object> objectToMap(Object object){
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                map.put(field.getName(),field.get(object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return  map;
    }
}
