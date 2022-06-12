package com.liuli.boot.security.jwt.utils;

import com.liuli.boot.security.jwt.constant.TokenConstant;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Slf4j
public class JwtUtil {

    // 私钥
    public static String SIGN_KEY = TokenConstant.SIGN_KEY;

    // 私钥进行Base64编码
    public static String BASE64_SECURITY = Base64.getEncoder().encodeToString(SIGN_KEY.getBytes(StandardCharsets.UTF_8));

    /**
     * 解析jsonWebToken
     *
     * @param jwt token串
     * @return Claims
     */
    public static Claims parseJwtToken(String jwt) {
       try{
            return Jwts.parser()
                    .setSigningKey(Base64.getDecoder().decode(JwtUtil.BASE64_SECURITY))
                    .parseClaimsJws(jwt).getBody();
        } catch (JwtException ex) {
            log.error("token check failed:{}",ex);
            return null;
        }
    }

    /**
     * 校验jwt
     *
     * @param jwt token串
     * @return true 有效  false 失效
     */
    public static boolean checkToken(String jwt) {
        if (StringUtils.isEmpty(jwt)) {
            return false;
        }
        try{
             Jwts.parser().setSigningKey(Base64.getDecoder().decode(JwtUtil.BASE64_SECURITY)).parseClaimsJws(jwt);
        } catch (JwtException ex) {
            log.error("token check failed:{}",ex);
            return  false;
        }
        return true;
    }


    /**
     *  创建令牌
     * @param user
     * @param subject
     * @param ttlMillis
     * @return
     */
    public static String createJwtToken(Map<String, Object> user, String subject,  Long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //生成签名密钥
        byte[] apiKeySecretBytes = Base64.getDecoder().decode(BASE64_SECURITY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, 0, apiKeySecretBytes.length, "AES");

        //添加构成JWT的类
        JwtBuilder builder = Jwts.builder()
                //设置主题，可以是json数据
                .setSubject(subject)
                //设置签发者
                .setIssuer("sg")
                //设置签发时间
                .setIssuedAt(now)
                //采用HS265加密，第二个参数是秘钥
                .signWith(signatureAlgorithm, signingKey);

        //设置JWT参数
        user.forEach(builder::claim);

        //添加Token过期时间
        long expMillis = nowMillis + ttlMillis;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp).setNotBefore(now)  ;
        return builder.compact();
    }

    public static void main(String[] args) {
//        Map<String, Object> user = new HashMap<>();
//        user.put("id","123");
//        user.put("name","张三");
//        String token = createJWT(user, "zzz",1000L);
//        System.err.println(token);
        Claims claims = parseJwtToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6enoiLCJpc3MiOiJzZyIsImlhdCI6MTY1NDk1MzY5OSwibmFtZSI6IuW8oOS4iSIsImlkIjoiMTIzIiwiZXhwIjoxNjU0OTUzNzAwLCJuYmYiOjE2NTQ5NTM2OTl9.SQJBxKtBDuIT1bDoDeNoRRTKCPK_HgtX3ZXkuAPMZAw");
        Object id = claims.get("id");
        System.err.println(id);
    }
}
