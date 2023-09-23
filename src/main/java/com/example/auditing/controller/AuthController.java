package com.example.auditing.controller;

import com.example.auditing.dto.LoginRequest;
import com.example.auditing.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtService jwtService;

  @PostMapping("/auth/login")
  public String login(@RequestBody @Validated LoginRequest loginRequest) {
    String username = loginRequest.getUsername();

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(username,
            loginRequest.getPassword()));

    if (authentication.isAuthenticated()) {
      return jwtService.generateToken(username);
    } else {
      throw new UsernameNotFoundException(String.format("User " +
          "name %s not found", username));
    }
  }
}