package com.example.usermanagement.model;

import com.example.usermanagement.audit.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Role extends Auditable<String> implements Serializable {
  @Id
  @GeneratedValue
  private Integer id;

  @NotNull(message = "role name cannot be null")
  @NotEmpty(message = "role name cannot be empty")
  @Column(unique = true)
  private String roleName;

  @OneToOne(mappedBy = "role")
  private User user;

  public Role(String roleName) {
    this.roleName = roleName;
  }
}
