package com.expense.tracker.play.config.security.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.expense.tracker.play.config.security.jwt.JwtConstants.AUTH_HEADER;
import static com.expense.tracker.play.config.security.jwt.JwtConstants.TOKEN_PREFIX;

/**
 * Access Token 인증 (사용자 인증 후 발급된 JWT Token)
 */
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    private final UserDetailsService userDetailsService;
    private final JwtConfig jwtConfig;

    public JwtAuthorizationFilter(UserDetailsService userDetailsService, JwtConfig jwtConfig) {
        this.userDetailsService = userDetailsService;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        String authorizationHeader = request.getHeader(AUTH_HEADER);

        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.replace(TOKEN_PREFIX, StringUtils.EMPTY);

        try {

            if (token != null) {

                String username = Jwts.parser()
                        .setSigningKey(jwtConfig.getSecretKey().getBytes())
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                            = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }

            }

        } catch (JwtException e) {
            throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
        }

        filterChain.doFilter(request, response);
    }

}

