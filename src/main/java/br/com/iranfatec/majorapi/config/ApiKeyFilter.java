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

        // Captura dinamicamente de qual site a requisição está vindo (Vercel ou localhost)
        String origin = request.getHeader("Origin");
        if (origin == null) {
            origin = "https://major-frontend-beta.vercel.app";
        }

        // 1. Intercepta requisições OPTIONS e valida dinamicamente a origem
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "*");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // 2. Libera o endpoint /health para o cron-job.org
        if ("/health".equals(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Verifica se o Header X-API-KEY bate com a senha configurada
        String requestApiKey = request.getHeader("X-API-KEY");

        if (apiKey == null || !apiKey.equals(requestApiKey)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Unauthorized: Invalid or missing API Key\"}");
            return;
        }

        // Garante que as requisições de sucesso também levem o cabeçalho correto para o navegador
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Credentials", "true");

        filterChain.doFilter(request, response);
    }
}
