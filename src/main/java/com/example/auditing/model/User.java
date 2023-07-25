package com.example.auditing.model;

import com.example.auditing.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.List;

@Entity
// user is a reserved word in PostgreSQL, hence using users
@Table(name = "_user")
@Data
public class User extends Auditable<String> implements UserDetails {
  @Id
  @GeneratedValue
  Long employeeId;

  @NotNull(message = "e-mail cannot be null")
  @NotEmpty(message = "e-mail cannot be empty")
  @Email(message = "e-mail invalid", regexp = "^[a-zA-Z0-9_!#$" +
      "%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
  @Column(unique = true)
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

  @JsonIgnore
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "role_id", referencedColumnName = "id")
  private Role role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
    grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
    return grantedAuthorities;
  }
}