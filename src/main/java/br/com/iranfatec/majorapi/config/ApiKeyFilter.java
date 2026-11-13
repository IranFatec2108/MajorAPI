package br.com.iranfatec.majorapi.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    @Value("${API_KEY}")
    private String apiKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // 1. Libera o endpoint /health para o cron-job.org e requisições OPTIONS (preflight do CORS)
        if ("/health".equals(path) || "OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Verifica se o Header X-API-KEY bate com a senha configurada
        String requestApiKey = request.getHeader("X-API-KEY");

        if (apiKey == null || !apiKey.equals(requestApiKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Unauthorized: Invalid or missing API Key\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
