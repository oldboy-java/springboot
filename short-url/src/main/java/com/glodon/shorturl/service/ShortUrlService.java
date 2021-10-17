package com.glodon.shorturl.service;

public interface ShortUrlService {

    /**
     *  生成短地址
     * @param sourceUrl  原始长地址
     * @param validAccessTime 有效访问时长 毫秒
     * @return
     */
    public String generateShortUrl(String sourceUrl, Long validAccessTime);

    /**
     * 根据短地址编码获取原始地址
     * @param shortCode
     * @return
     */
    String getSourceUrl(String shortCode);
}
