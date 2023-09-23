package com.example.auditing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HelloController {
  @GetMapping("/")
  public String greeting() {
    return "You have hit a non-secured end point";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public String adminOnlyURL() {
    return "You have hit a secured admin only end point";
  }

  @GetMapping("/user")
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public String userOnlyURL() {
    return "You have hit a secured user only end point";
  }
}
