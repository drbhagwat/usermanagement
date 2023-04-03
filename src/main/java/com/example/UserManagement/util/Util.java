package com.example.UserManagement.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class Util {
  private Util() {}

  public static String getLoggedInUserName() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }
}
