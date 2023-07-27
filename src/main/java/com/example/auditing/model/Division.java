package com.example.auditing.model;

import com.example.auditing.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Division extends Auditable<String> implements Serializable {
  @Id
  @GeneratedValue
  private long id;

  @NotNull(message = "division name cannot be null")
  @NotEmpty(message = "division name cannot be empty")
  @Column(unique = true)
  private String name;

  @JsonIgnore
  @OneToMany(mappedBy = "division", cascade = CascadeType.ALL, orphanRemoval
      = true)
  private List<Department> departments = new ArrayList<>();

  public void addDepartment(Department department) {
    departments.add(department);
    department.setDivision(this);
  }

  public void removeDepartment(Department department) {
    departments.remove(department);
    department.setDivision(null);
  }
}
