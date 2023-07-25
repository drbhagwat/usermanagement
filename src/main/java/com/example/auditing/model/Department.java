package com.example.auditing.model;

import com.example.auditing.audit.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class Department extends Auditable<String> implements Serializable {
  @Id
  @GeneratedValue
  private long id;

  @NotNull(message = "department name cannot be null")
  @NotEmpty(message = "department name cannot be empty")
  @Column(unique = true)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  private Division division;
}
