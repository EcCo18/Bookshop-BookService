package com.example.userservice.security.jwt;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenValidationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        logger.info("Request received for Jwt token validation");
        JwtTokenValidator validator = new JwtTokenValidator();
        validator.validateJwtToken(request, response, false);
        filterChain.doFilter(request, response);

    }
}
