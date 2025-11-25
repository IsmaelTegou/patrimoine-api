package com.ktiservice.patrimoine.infrastructure.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktiservice.patrimoine.infrastructure.web.dto.common.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Handle access denied errors (403 Forbidden).
 * Returns JSON error response instead of default HTML.
 */
@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException ex) throws IOException, ServletException {
        log.error("Responding with access denied error. Message: {}", ex.getMessage());

        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(403)
                .message("Access Denied - You do not have permission to access this resource")
                .errorCode("FORBIDDEN")
                .timestamp(LocalDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .build();

        ObjectMapper mapper = new ObjectMapper();
        httpServletResponse.getWriter().write(mapper.writeValueAsString(errorResponse));
    }
}
