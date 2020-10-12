package com.prj.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * jwt工具类
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "prj.jwt")
public class JwtUtils {
    private static final String SECRET_KEY = "123456";
    private String secret;
    private long expire;
    private String header;

    /**secret
     * 生成jwt token
     */
    public String generateToken(long userId) {
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(userId+"")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            log.debug("validate is token error ", e);
            return null;
        }
    }

    /**
     * token是否过期
     * @return  true：过期
     */
    public boolean isTokenExpired(Date expiration) {
        String jwt = Jwts
                .builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                //expTime是过期时间
                .setExpiration(expiration)
                .claim("username", "wang")
                .claim("password", "111111")
                //该方法是在JWT中加入值为vaule的key字段
                .claim("key", "vaule")
                .compact();

        return expiration.before(new Date());
    }
}

