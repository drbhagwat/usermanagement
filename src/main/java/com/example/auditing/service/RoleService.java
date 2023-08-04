package com.example.auditing.service;

import com.example.auditing.repository.RoleRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleService {
  private final RoleRepository roleRepository;
}