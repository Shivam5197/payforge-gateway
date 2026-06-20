package com.payforge.gateway.common.security;

import com.payforge.gateway.apikey.entity.ApiKey;
import com.payforge.gateway.apikey.exception.InvalidAPIKeyException;
import com.payforge.gateway.apikey.repository.ApiKeyRepository;
import com.payforge.gateway.common.utility.HashUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class ApiKeyAuthenticationFilter   extends OncePerRequestFilter {

    private final ApiKeyRepository apiKeyRepository;
    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();

        if (isPublicEndpoint(uri)) {
            filterChain.doFilter(request, response);
            return;
        }

        String apiKey =
                request.getHeader("X-API-KEY");

        if (apiKey == null || apiKey.isBlank()) {
            unauthorized(response, "Missing API Key");
            return;
        }

        String keyHash =
                HashUtil.sha256(apiKey);

        ApiKey storedKey =
                apiKeyRepository.findByKeyHash(keyHash)
                        .orElseThrow(() -> new InvalidAPIKeyException("Invalid API Key"));

        MerchantContext.set(
                storedKey.getMerchant());

        if (!storedKey.isActive()) {
            throw new InvalidAPIKeyException(
                    "API Key is inactive");
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            MerchantContext.clear();
        }
    }

    private boolean isPublicEndpoint(
            String uri) {

        return uri.startsWith("/api/v1/merchants")
                || uri.startsWith("/swagger-ui")
                || uri.startsWith("/v3/api-docs");
    }

    private void unauthorized(
            HttpServletResponse response,
            String message)
            throws IOException {

        response.setStatus(
                HttpServletResponse.SC_UNAUTHORIZED);

        response.setContentType(
                "application/json");

        response.getWriter().write(
                """
                {
                    "errorCode":"INVALID_API_KEY",
                    "message":"%s"
                }
                """.formatted(message));
    }
}
