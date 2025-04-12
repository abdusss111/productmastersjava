package org.example.authservice.filter;


import com.example.authservice.security.JwtUtil;
import com.example.authservice.service.AuthService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AuthService authService;

    public JwtTokenFilter(JwtUtil jwtUtil, AuthService authService) {
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getJwtFromRequest(request);
        if (token != null && jwtUtil.validateToken(token, authService.getUserFromToken(token))) {
            String username = jwtUtil.extractUsername(token);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, null); // In a real case, you'd use authorities/roles here
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);  // Continue the filter chain
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No special initialization needed
    }

    @Override
    public void destroy() {
        // Clean up resources if necessary
    }
}

