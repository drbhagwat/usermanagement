package com.example.auditing.service;

import com.example.auditing.exception.ResourceNotFoundException;
import com.example.auditing.model.Role;
import com.example.auditing.model.User;
import com.example.auditing.repository.RoleRepository;
import com.example.auditing.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService implements UserDetailsService {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  @Override
  public UserDetails loadUserByUsername(String userName) throws
      UsernameNotFoundException {
    return userRepository.findByUsername(userName)
        .orElseThrow(() -> new UsernameNotFoundException(String.format("User " +
            "name %s not found", userName)));
  }

  public User find(long id) {
    return userRepository.findById(id).orElseThrow(() ->
        new ResourceNotFoundException("User with employee id " + id + " Not" +
            " Found!"));
  }

  public List<User> getAll() {
    return userRepository.findAll();
  }

  public User add(User user) {
    init(user); // initialize other fields properly
    return userRepository.save(user);
  }

  @Transactional
  public User update(long id, User newUser) {
    User existingUser = find(id);
    existingUser.setUsername(newUser.getUsername());
    existingUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
    String roleName = "ROLE_" + newUser.getRoleName();
    existingUser.setRoleName(roleName);
    return userRepository.save(existingUser);
  }

  @Transactional
  public void delete(long id) {
    User existingUser = find(id);
    userRepository.delete(existingUser); // always delete the current user
  }

  public void init(User user) {
    // encrypt the plain text password input by end user
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    // set other fields properly
    user.setAccountNonExpired(true);
    user.setAccountNonLocked(true);
    user.setEnabled(true);
    user.setCredentialsNonExpired(true);
    String roleName = "ROLE_" + user.getRoleName();
    user.setRoleName(roleName);
    user.setRole(roleRepository.save(new Role(roleName)));
  }
}