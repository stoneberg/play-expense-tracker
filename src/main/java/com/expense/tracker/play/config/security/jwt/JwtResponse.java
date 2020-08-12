package com.expense.tracker.play.config.security.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Builder
@ToString
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -589054060640206942L;
    private final Long id;
    private final String username;
    private final String email;
    private final String token;
    private final List<String> roles;
}