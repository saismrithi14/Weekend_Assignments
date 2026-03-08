package com.order_management.Order_management_system.security;

import com.order_management.Order_management_system.dto.LoginDTO;
import com.order_management.Order_management_system.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    private final AuthenticationManager authManager;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    public AuthService(AuthenticationManager authManager)
    {
        this.authManager = authManager;
    }

    public ResponseEntity<String> authenticateUser(LoginDTO dto)
    {
       try
       {
           authManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
           String token = jwtUtil.generateToken(dto.getUsername());
           return ResponseEntity.ok(token);
       }

       catch(AuthenticationException e)
       {
           System.out.println(e.getMessage());
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
       }
    }
}


