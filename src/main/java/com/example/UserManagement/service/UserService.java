package com.example.UserManagement.service;

import com.example.UserManagement.enums.UserRole;
import com.example.UserManagement.model.Role;
import com.example.UserManagement.model.User;
import com.example.UserManagement.repository.RoleRepository;
import com.example.UserManagement.repository.UserRepository;
import com.example.UserManagement.util.Util;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService implements UserDetailsService {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  @Override
  public UserDetails loadUserByUsername(String userName) throws
      UsernameNotFoundException {
    return userRepository.findById(userName)
        .orElseThrow(() -> new UsernameNotFoundException(String.format("User " +
            "name %s not found", userName)));
  }

  public User find(String userName) {
    Optional<User> optionalUser = userRepository.findById(userName);
    return optionalUser.isEmpty() ? null : optionalUser.get();
  }

  public User get(String userName) {
    return find(userName);
  }

  public List<User> getAll() {
    return userRepository.findAll();
  }

  public boolean add(User user) {
    User existingUser = find(user.getUsername());

    if (existingUser != null) {
      return false; // user already exists - indicate an error
    }
    init(user); // initialize other fields properly
    userRepository.save(user);
    return true; // everything worked fine, so return true */
  }

  @Transactional
  public User update(User newUser) {
    User existingUser = find(newUser.getUsername());

    if (existingUser == null) {
      return null;
    }
    /* password input by end user is plain text, encrypt it, password can be
    changed by the non-admin user but only for himself or herself
     */
    existingUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
    Role existingRole = getRole(existingUser);
    String adminRoleName = UserRole.ROLE_ADMIN.getRoleName();
    String loggedInUserRoleName = getRoleName(getLoggedInUser());

    // if the logged-in user is admin
    if (loggedInUserRoleName.equals(adminRoleName)) {
      // get list of admins
      List<User> usersWithAdminRole =
          userRepository.findUsersWithRole(adminRoleName);
      String newRoleName = newUser.getRoleName();

      // is there only one admin in the system
      if (usersWithAdminRole.size() == 1) {

        // admin is updating someone else
        if (!getNameOfLoggedInUser()
            .equalsIgnoreCase(existingUser.getUsername())) {
          existingUser.setRoleName(newRoleName);
          existingRole.setRoleName(newRoleName);
        }
      } else { // there are other admins too in the system
        existingUser.setRoleName(newRoleName);
        existingRole.setRoleName(newRoleName);
      }
    }
    return userRepository.save(existingUser);
  }

  @Transactional
  public User delete(String userName) {
    User existingUser = find(userName);

    if (existingUser == null) {
      return null;
    }
    Role existingRole = getRole(existingUser);
    String existingRoleName = existingRole.getRoleName();
    String userRole = UserRole.ROLE_USER.getRoleName();
    String adminRole = UserRole.ROLE_ADMIN.getRoleName();
    // get list of admins
    List<User> usersWithAdminRole =
        userRepository.findUsersWithRole(adminRole);
    // get list of users
    List<User> usersWithUserRole =
        userRepository.findUsersWithRole(userRole);

    // the current user is USER
    if (existingRoleName.equalsIgnoreCase(userRole)) {
      existingUser.removeRole(existingRole);

      // if this is the last user, then delete the role too
      if (usersWithUserRole.size() == 1) {
        roleRepository.delete(existingRole);
      }
      userRepository.delete(existingUser); // always delete the current user
    } else { // current user is the ADMIN

      if (usersWithAdminRole.size() == 1 && !usersWithUserRole.isEmpty()) {
        return null;
      }
      existingUser.removeRole(existingRole);

      // if this is the last admin, and there are no other users in the
      // system, only then delete the role
      if (usersWithUserRole.isEmpty()) {
        roleRepository.delete(existingRole);
      }
      userRepository.delete(existingUser); // always delete the current user
    }
    return existingUser;
  }

  public void init(User user) {
    // encrypt the plain text password input by end user
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    // set other fields properly
    user.setAccountNonExpired(true);
    user.setAccountNonLocked(true);
    user.setEnabled(true);
    user.setCredentialsNonExpired(true);

    String roleName = null;

    if (getNumberOfUsers() == 0) {
      roleName = UserRole.ROLE_ADMIN.getRoleName();
    } else {
      roleName = user.getRoleName();
    }
    user.setRoleName(roleName);

    // create a new Role only if it does not exist in the role table
    Role role = roleRepository.findByRoleName(roleName);

    if (role == null) {
      role = new Role(roleName);
    }
    user.addRole(roleRepository.save(role));
  }

  public User getLoggedInUser() {
    return get(Util.getLoggedInUserName());
  }

  public String getRoleNameOfLoggedInUser() {
    return getLoggedInUser().getRoles().stream().findFirst().get().getRoleName();
  }

  public String getRoleName(User user) {
    return getRole(user).getRoleName();
  }

  public Role getRole(User user) {
    // get the role of the given user
    return user.getRoles().stream().findFirst().get();
  }

  public String getNameOfLoggedInUser() {
    return getLoggedInUser().getUsername();
  }

  public int getNumberOfUsers() {
    // get number of users in the system
    return getAll().size();
  }
}
