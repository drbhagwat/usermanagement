package com.example.auditing.model;

import com.example.auditing.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
public class Designation extends Auditable<String> implements Serializable {
  @Id
  @GeneratedValue
  private long id;

  @NotNull(message = "designation name cannot be null")
  @NotEmpty(message = "desingation name cannot be empty")
  @Column(unique = true)
  private String designationName;

  @JsonIgnore
  @OneToOne(mappedBy = "designation")
  private Role role;

  public Designation(String designationName) {
    this.designationName = designationName;
  }
}
