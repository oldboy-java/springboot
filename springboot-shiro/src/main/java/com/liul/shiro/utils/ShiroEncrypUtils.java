package com.liul.shiro.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

public class ShiroEncrypUtils {
    private ShiroEncrypUtils() {
    }

    /**
     * 获取加密密文  （用户注册时使用此方法进行加密）
     *
     * @param algorithmName  加密算法
     * @param password       明文
     * @param salt           盐值
     * @param hashIterations 加密次数
     * @return
     */
    public static Object getCiphertext(String algorithmName, String password, String salt, int hashIterations) {
        //        ByteSource salt = ByteSource.Util.bytes(username);
        // 使用缓存用ByteSource会java.io.NotSerializableException: org.apache.shiro.util.SimpleByteSource
        SerializableByteSource sbs = new SerializableByteSource(salt);
        Object result = new SimpleHash(algorithmName, password, sbs, hashIterations);
        return result;
    }

    public static void main(String[] args) {
        System.out.println(getCiphertext("md5", "123456", "user", 10));
    }
}
