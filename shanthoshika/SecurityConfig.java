package com.tourism.customizationplatform.config;

import com.tourism.customizationplatform.model.User;
import com.tourism.customizationplatform.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityConfig extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        String method = request.getMethod();

        // Allow GET /api/packages and /api/packages/sorted without authentication
        if (path.startsWith("/api/packages") && method.equals("GET") || path.equals("/api/packages/sorted")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Allow GET /api/bookings with userId query parameter or User-Id header
        if (path.startsWith("/api/bookings") && method.equals("GET")) {
            String userId = request.getParameter("userId");
            if (userId == null) {
                userId = request.getHeader("User-Id");
            }
            if (userId == null) {
                response.setStatus(401);
                response.getWriter().write("userId parameter or User-Id header is required");
                return;
            }
            try {
                User user = userService.findUserById(userId);
                if (user == null) {
                    response.setStatus(403);
                    response.getWriter().write("Invalid user ID");
                    return;
                }
            } catch (IllegalArgumentException e) {
                response.setStatus(400);
                response.getWriter().write("Invalid user ID format");
                return;
            }
            filterChain.doFilter(request, response);
            return;
        }

        // User endpoints (PUT /api/users)
        if (path.startsWith("/api/users") && method.equals("PUT")) {
            String userId = request.getHeader("User-Id");
            if (userId == null) {
                response.setStatus(401);
                response.getWriter().write("User-Id header is required");
                return;
            }
            User user = userService.findUserById(userId);
            if (user == null) {
                response.setStatus(403);
                response.getWriter().write("Invalid user ID");
                return;
            }
        }

        // Admin-only endpoints (POST, PUT, DELETE for /api/packages, /api/admins)
        if ((path.startsWith("/api/packages") && !method.equals("GET")) || path.startsWith("/api/admins")) {
            String adminId = request.getHeader("Admin-Id");
            if (adminId == null) {
                response.setStatus(401);
                response.getWriter().write("Admin-Id header is required");
                return;
            }
            User user = userService.findUserById(adminId);
            if (!(user instanceof com.tourism.customizationplatform.model.AdminUser)) {
                response.setStatus(403);
                response.getWriter().write("Access restricted to admins");
                return;
            }
        }

        // User-only endpoints (PUT, DELETE for /api/bookings)
        if (path.startsWith("/api/bookings") && (method.equals("PUT") || method.equals("DELETE"))) {
            String userId = request.getHeader("User-Id");
            if (userId == null) {
                response.setStatus(401);
                response.getWriter().write("User-Id header is required");
                return;
            }
            User user = userService.findUserById(userId);
            if (user == null) {
                response.setStatus(403);
                response.getWriter().write("Invalid user ID");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
