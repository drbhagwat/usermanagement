package com.example.UserManagement.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
  ROLE_ADMIN("ROLE_ADMIN"), ROLE_USER ("ROLE_USER");
  private String roleName;
}
