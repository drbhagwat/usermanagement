package com.example.UserManagement.model;

import com.example.UserManagement.audit.Audit;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
// as user is a reserved word in PostgreSQL, using users
@Table(name = "_user")
@Data
public class User extends Audit implements UserDetails {
  @Id
  @NotNull(message = "e-mail cannot be null")
  @NotEmpty(message = "e-mail cannot be empty")
  @Email(message = "e-mail invalid", regexp = "^[a-zA-Z0-9_!#$" +
      "%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
  private String username;

  @NotNull(message = "password cannot be null")
  @NotEmpty(message = "password cannot be empty")
  @Size(min = 8, message = "password should have at least " +
      " 8 characters")
  private String password;
  private boolean accountNonExpired;
  private boolean accountNonLocked;
  private boolean enabled;
  private boolean credentialsNonExpired;
  private String roleName;

  @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,
      CascadeType.MERGE})
  @JoinTable(name = "_user_role",
      joinColumns = {@JoinColumn(name = "username")},
      inverseJoinColumns = {@JoinColumn(name = "id")})
  private Collection<Role> roles = new HashSet<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

    for (Role r : this.getRoles()) {
      grantedAuthorities.add(new SimpleGrantedAuthority(r.getRoleName()));
    }
    return grantedAuthorities;
  }

  public void addRole(Role role) {
    this.roles.add(role);
    role.getUsers().add(this);
  }

  public void removeRole(Role role) {
    this.roles.remove(role);
    role.getUsers().remove(this);
  }
}