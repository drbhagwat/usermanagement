package com.example.auditing.repository;

import com.example.auditing.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer>  {
}
