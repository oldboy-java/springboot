package com.liuli.boot.security.jwt.utils;

import lombok.SneakyThrows;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;

public final class WebUtils {

    @SneakyThrows
    public static void renderJson(HttpServletResponse response, String json){
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
