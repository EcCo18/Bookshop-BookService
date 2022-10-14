package com.example.userservice.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class JwtTokenCreator {

    public void generateToken(HttpServletRequest request, HttpServletResponse response) {

        //Get the username from authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) { //verify whether user is authenticated
            String username = authentication.getName();
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

            String jwt_token = Jwts.builder()
                    .setIssuer("book-management")
                    .setExpiration(new Date((new Date()).getTime() + 300000))
                    .setSubject("book_management_token")
                    .claim("username", username)
                    .claim("authorities", getStudentRoles((List<GrantedAuthority>) authentication.getAuthorities()))
                    .signWith(key)
                    .compact();

            if (request.getHeader(SecurityConstants.REFRESH_HEADER) == null) {

                String refresh_token = Jwts.builder()
                        .setIssuer("javainfinite")
                        .setExpiration(new Date((new Date()).getTime() + 3000000))
                        .setSubject("javainfinite_token")
                        .claim("username", username)
                        //.claim("authorities", getStudentRoles((List<GrantedAuthority>) authentication.getAuthorities()))
                        .signWith(key)
                        .compact();

                response.setHeader(SecurityConstants.REFRESH_HEADER, refresh_token);
                log.info("Refresh Token successfully generated: {}", refresh_token);
            }
            response.setHeader(SecurityConstants.AUTHORIZATION_HEADER, jwt_token);
            log.info("Token successfully generated: {}", jwt_token);
        }
    }

    private String getStudentRoles(List<GrantedAuthority> collection) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }
}
