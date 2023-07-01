package com.example.usermanagement.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
  ROLE_ADMIN("ROLE_ADMIN"),
  ROLE_MGR("ROLE_MGR"),
  ROLE_DM("ROLE_DM"),
  ROLE_AM("ROLE_AM"),
  ROLE_GL("ROLE_GL"),
  ROLE_TL("ROLE_TL"),
  ROLE_TM ("ROLE_TM");

  private String roleName;
}
