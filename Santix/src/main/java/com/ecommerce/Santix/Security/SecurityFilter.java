package com.ecommerce.Santix.Security;

import com.ecommerce.Santix.Exception.UnauthorizedException;
import com.ecommerce.Santix.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            var token = recoverToken(request);

            if (token != null) {
                var email = tokenService.validateToken(token);

                var user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new UnauthorizedException("Usuário não encontrado"));

                var authentication = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            String json = """
                        {
                            "code": "INVALID_TOKEN",
                            "message": "%s",
                            "timestamp": "%s"
                        }
                    """.formatted(e.getMessage(), java.time.LocalDateTime.now());

            response.getWriter().write(json);
            return;
        }
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        if (authHeader == null) return null;

        return authHeader.replace("Bearer ", "");
    }
}
