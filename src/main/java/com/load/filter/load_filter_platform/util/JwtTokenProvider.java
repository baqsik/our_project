package com.load.filter.load_filter_platform.util;

import com.load.filter.load_filter_platform.model.entity.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.tokensecret}")
    private String jwtSecret;

    @Value("${jwt.token.expired}")
    private long validityInMilliseconds;
    private final Date exp = new Date((new Date()).getTime() + 5000000);

    public String generateToken(User user) {

        Map<String, Object> map = new HashMap<>();
        map.put("role", user.getRole().getName());
        map.put("username", user.getUsername());
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setClaims(map)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .setExpiration(exp)
                .compact();
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return String.valueOf(claims.get("username"));
    }

    public Collection<GrantedAuthority> getRole(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        String role = (String) claims.get("role");

        return List.of(new SimpleGrantedAuthority(role));
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }
}
