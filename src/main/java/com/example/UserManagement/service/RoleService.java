package com.example.UserManagement.service;

import com.example.UserManagement.repository.RoleRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class RoleService {
  private final RoleRepository roleRepository;
}