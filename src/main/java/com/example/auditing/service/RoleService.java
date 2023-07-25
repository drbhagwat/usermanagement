package com.example.auditing.service;

import com.example.auditing.repository.RoleRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class RoleService {
  private final RoleRepository roleRepository;
}