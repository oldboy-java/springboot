package com.glodon.shorturl.controller;


import com.glodon.shorturl.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    /**
     * 生成短地址
     *
     * @param url             长地址
     * @param validAccessTime 短地址访问有效时长，毫秒数，-1长期有效
     * @return
     */
    @GetMapping("/short-urls/generate")
    public String generateShortUrl(@RequestParam("url") String url, @RequestParam(name = "validAccessTime", required = false, defaultValue = "-1") long validAccessTime) {
        return "http://127.0.0.1:8080/" + shortUrlService.generateShortUrl(url, validAccessTime);
    }


    /**
     * 根据短地址编码重定向原始地址   http://127.0.0.1:8080/Q7NZbu
     *
     * @param shortCode 短地址编码
     * @return
     */
    @GetMapping("/{shortCode}")
    public void accessShortUrl(@PathVariable("shortCode") String shortCode, HttpServletResponse response) {
        String sourceUrl = shortUrlService.getSourceUrl(shortCode);
        try {
            response.sendRedirect(sourceUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
