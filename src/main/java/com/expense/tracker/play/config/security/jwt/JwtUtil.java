package com.expense.tracker.play.config.security.jwt;

import com.expense.tracker.play.config.security.service.CustomUserDetails;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class JwtUtil {

    private final JwtConfig jwtConfig;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // SECRET_KEY property to generate the signing key, and uses that to verify token
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtConfig.getSecretKey()).build().parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(CustomUserDetails customUserDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", customUserDetails.getUsername());
        claims.put("email", customUserDetails.getEmail());
        return createToken(claims, customUserDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(jwtConfig.getSecretKey()),
                SignatureAlgorithm.HS256.getJcaName());
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getTokenExpiration()))
                .signWith(hmacKey).compact();
    }

    public void jwtAuthResponse(CustomUserDetails customUserDetails, HttpServletResponse response) throws IOException {
        JwtResponse jwtResponse = JwtResponse.builder()
                .id(customUserDetails.getId())
                .username(customUserDetails.getUsername())
                .email(customUserDetails.getEmail())
                .roles(customUserDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .token(generateToken(customUserDetails))
                .build();

        String json = new Gson().toJson(jwtResponse);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
