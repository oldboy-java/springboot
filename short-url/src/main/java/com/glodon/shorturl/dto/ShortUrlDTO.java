package com.glodon.shorturl.dto;

import lombok.Data;

@Data
public class ShortUrlDTO {
    /**
     * 长链接地址
     */
    private String url;

    /**
     * 有效访问时长：毫秒数
     */
    private long validAccessTime;

}
