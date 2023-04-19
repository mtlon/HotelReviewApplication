package com.api.hotelreviewapplication.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

import static jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String message;
        if (authException.getCause() != null) {
            message = authException.getCause().getMessage();
        } else {
            message = authException.getMessage();
        }
        byte[] body = new ObjectMapper().writeValueAsBytes(Collections.singletonMap("error", message));
        response.getOutputStream().write(body);
    }
}
