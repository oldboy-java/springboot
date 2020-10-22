package com.liul.shiro.utils;


import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * Created by chenxy on 2016/12/7.
 */
@Slf4j
public final class ObjectSerializeUtils {
    private static final String SERIALIZE_ERROR_MSG = "序列化失败";
    private static final String DESERIALIZE_ERROR_MSG = "反序列化失败";

    private ObjectSerializeUtils() {
    }

    public static <T> byte[] serialize(T value) {
        byte[] bytes = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);) {
            objectOutputStream.writeObject(value);
            bytes = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            log.error(SERIALIZE_ERROR_MSG, e);
        }
        return bytes;
    }

    public static <T> T deserialize(byte[] bytes) {
        if (null == bytes || bytes.length == 0) {
            return null;
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        return deserialize(byteArrayInputStream);
    }

    public static <T> T deserialize(InputStream inputStream) {

        try (ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);) {
            Object objValue = objectInputStream.readObject();
            if (null == objValue) {
                return null;
            }
            return (T) objValue;
        } catch (Exception e) {
            log.error(DESERIALIZE_ERROR_MSG, e);
        }
        return null;
    }
}
