package com.ktiservice.patrimoine.infrastructure.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktiservice.patrimoine.infrastructure.web.dto.common.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Handle authentication errors (401 Unauthorized).
 * Returns JSON error response instead of default HTML.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper;

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException ex) throws IOException, ServletException {
        log.error("Responding with unauthorized error. Message: {}", ex.getMessage());

        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(401)
                .message("Unauthorized - Authentication required")
                .errorCode("UNAUTHORIZED")
                .timestamp(LocalDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .build();

        httpServletResponse.getWriter().write(mapper.writeValueAsString(errorResponse));
    }
}