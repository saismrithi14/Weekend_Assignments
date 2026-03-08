package com.order_management.Order_management_system.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private JwtUtil jwtUtil;
    private UserDetailsService userDetailsService;

    public JwtAuthFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService)
    {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String auth_header = request.getHeader("Authorization");
        if(auth_header == null || !auth_header.startsWith("Bearer "))
        {
            filterChain.doFilter(request,response);
            return;
        }

        String token = auth_header.substring(7);
        String user_name = jwtUtil.extractUserName(token);
        if(user_name == null)
        {
            filterChain.doFilter(request,response);
            return;
        }

        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(user_name);
            if(jwtUtil.isValidToken(token, userDetails, user_name))
            {
                UsernamePasswordAuthenticationToken authenticated_user = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticated_user);
            }
        }
        catch(UsernameNotFoundException ue)
        {
            filterChain.doFilter(request,response);
            return;
        }

        filterChain.doFilter(request,response);
    }
}
