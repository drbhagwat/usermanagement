package com.example.auditing.model;

import com.example.auditing.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
public class Role extends Auditable<String> implements Serializable {
  @Id
  @GeneratedValue
  private long id;

  @NotNull(message = "role name cannot be null")
  @NotEmpty(message = "role name cannot be empty")
  private String roleName;

  @JsonIgnore
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "designation_id", referencedColumnName = "id")
  private Designation designation;

  @JsonIgnore
  @OneToOne(mappedBy = "role")
  private User user;

  public Role(String roleName) {
    this.roleName = roleName;
  }
}
