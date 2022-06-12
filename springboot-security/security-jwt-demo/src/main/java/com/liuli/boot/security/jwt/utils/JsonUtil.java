package com.liuli.boot.security.jwt.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ognl.Ognl;
import ognl.OgnlContext;


import java.util.Map;

public class JsonUtil {

    /**
     * 将制定JSON转为Map，Key固定为String, 对应JSONKey，
     * Value分情况：
     * 1、value是字符串，自动转为字符串，例如：{“a","b"}
     * 2、value是其他JSON对象，自动转换为Map，如{“a":{"b":"2"}}
     * 3、value是数组，自动转为List<Map>,如{“a":[{"b":"2"},[{"c":"3"}}
     * @param json
     * @return
     */
    public static Map<String,Object> transferToMap(String json) {
        Gson gson = new Gson();
        Map<String,Object> map = gson.fromJson(json, new TypeToken<Map<String, Object>>() {
        }.getType());
        return map;
    }

    /**
     *  根据json路径从json中获取对应值
     * @param json 原始的JSON串
     * @param path OGNL规则表达式
     * @param clazz value对应的目标类
     * @param <T> clazz对应数据
     */
    public static <T> T getValue(String json, String path, Class<T> clazz) {
        try{
            Map map =  transferToMap(json);
            OgnlContext context = new OgnlContext();
            context.setRoot(map);
            T value = (T) Ognl.getValue(path, context, context.getRoot());
            return value;
        }catch (Exception ex) {
            throw  new RuntimeException(ex);
        }
    }



    /**
     *  根据json路径从json中获取对应值
     * @param map 原始的JSON串转Map对象
     * @param path OGNL规则表达式
     * @param clazz value对应的目标类
     * @param <T> clazz对应数据
     */
    public static <T> T getValue(Map map , String path, Class<T> clazz) {
        try{
            OgnlContext context = new OgnlContext();
            context.setRoot(map);
            T value = (T) Ognl.getValue(path, context, context.getRoot());
            return value;
        }catch (Exception ex) {
            throw  new RuntimeException(ex);
        }
    }


}
