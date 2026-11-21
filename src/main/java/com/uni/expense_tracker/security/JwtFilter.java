package com.uni.expense_tracker.security;

import com.uni.expense_tracker.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        String email = null;
        String jwt = null;

        // 1. Check if the request has a Bearer token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7); // Remove "Bearer " from the string

            try {
                email = jwtUtil.extractEmail(jwt);
            } catch (Exception e) {
                // Token is invalid or expired
            }
        }

        // 2. If we found an email and they aren't already logged in
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Fetch the user from the DB
            var userOpt = userRepository.findByEmail(email);

            if (userOpt.isPresent()) {
                UserDetails userDetails = org.springframework.security.core.userdetails.User
                        .withUsername(userOpt.get().getEmail())
                        .password(userOpt.get().getPassword())
                        .authorities("USER") // We aren't doing roles yet
                        .build();

                // 3. Create the authentication token and set it in Spring's context
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 4. Move to the next filter
        filterChain.doFilter(request, response);
    }
}