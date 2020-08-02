package com.expense.tracker.play.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.expense.tracker.play.config.security.jwt.JwtConstants.AUTH_HEADER;
import static com.expense.tracker.play.config.security.jwt.JwtConstants.TOKEN_PREFIX;

/**
 * 사용자 Credential 인증
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        setFilterProcessesUrl(JwtConstants.SING_IN_URL);
    }

    /**
     * 인증을 위해 request에서 username, password를 추출, UsernamePasswordAuthenticationToken에 적재
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            JwtRequest credentials = new ObjectMapper().readValue(req.getInputStream(), JwtRequest.class);
            logger.info("@credentials=================>{}", credentials);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername(),
                    credentials.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 인증 성공 시 JWT Token 발급
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        long timestamp = System.currentTimeMillis();

        String token = Jwts.builder()
                .setSubject(auth.getName())
                //.claim("authorities", auth.getAuthorities())
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + jwtConfig.getTokenExpiration()))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecretKey().getBytes())
                .compact();

        logger.info("@issuing token=================>{}", token);
        res.addHeader(AUTH_HEADER, TOKEN_PREFIX + token);
    }
}
