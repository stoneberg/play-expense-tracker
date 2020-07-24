package com.expense.tracker.play.common.utils;

import com.expense.tracker.play.user.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

    @Value("${app.jwt.secret-key}")
    private String secretKey;

    // 2 X 60 X 60 X 1000 => 2시간
    @Value("${app.jwt.token-validity}")
    private Long tokenValidity;

    public Map<String, Object> generateJwtToken(User user) {
        long timestamp = System.currentTimeMillis();
        String jwtToken = Jwts.builder().signWith(SignatureAlgorithm.HS256, secretKey)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + tokenValidity))
                .claim("email", user.getEmail())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .compact();
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", jwtToken);
        return tokenMap;
    }
}
