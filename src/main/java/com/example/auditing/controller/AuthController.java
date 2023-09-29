package com.example.auditing.controller;

import com.example.auditing.dto.JwtResponse;
import com.example.auditing.dto.LoginRequest;
import com.example.auditing.model.RefreshToken;
import com.example.auditing.service.JwtService;
import com.example.auditing.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
  @Autowired
  private RefreshTokenService refreshTokenService;

  @PostMapping("/auth/login")
  public JwtResponse login(@RequestBody @Validated LoginRequest loginRequest) {
    String username = loginRequest.getUsername();

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(username,
            loginRequest.getPassword()));

    if (authentication.isAuthenticated()) {
      RefreshToken refreshToken = refreshTokenService.createRefreshToken(username);
      return JwtResponse.builder()
          .jwtAccessToken(jwtService.generateToken(username))
          .token(refreshToken.getToken()).build();
    } else {
      throw new UsernameNotFoundException(String.format("User " +
          "name %s not found", username));
    }
  }

  @PostMapping("/auth/refreshToken")
  public JwtResponse refreshToken(@RequestBody @Validated RefreshToken refreshToken) {
    return refreshTokenService.findByToken(refreshToken.getToken())
        .map(refreshTokenService::verifyExpiration)
        .map(RefreshToken::getUser)
        .map( user -> {
          String jwtAccessToken = jwtService.generateToken(user.getUsername());
          return JwtResponse.builder().jwtAccessToken(jwtAccessToken)
              .token(refreshToken.getToken()).build();
        }).orElseThrow( () -> new RuntimeException("Refresh token is not in " +
            "the database"));
  }
}