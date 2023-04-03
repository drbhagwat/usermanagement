package com.example.usermanagement.service;

import com.example.usermanagement.repository.RoleRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class RoleService {
  private final RoleRepository roleRepository;
}