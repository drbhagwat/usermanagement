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
public class Department extends Auditable<String> implements Serializable {
  @Id
  @GeneratedValue
  private long id;

  @NotNull(message = "department name cannot be null")
  @NotEmpty(message = "department name cannot be empty")
  private String name;

  @JsonIgnore
  @OneToMany(mappedBy = "department", cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Line> lines = new ArrayList<>();

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  private Division division;

  public void addLine(Line line) {
    lines.add(line);
    line.setDepartment(this);
  }

  public void removeLine(Line line) {
    lines.remove(line);
    line.setDepartment(null);
  }
}