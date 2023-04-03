package com.example.UserManagement.repository;

import com.example.UserManagement.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer>  {
  Role findByRoleName(String roleName);
}
