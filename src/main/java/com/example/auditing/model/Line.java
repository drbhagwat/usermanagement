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
public class Line extends Auditable<String> implements Serializable {
  @Id
  @GeneratedValue
  private long id;

  @NotNull(message = "line name cannot be null")
  @NotEmpty(message = "line name cannot be empty")
  private String name;

  @JsonIgnore
  @OneToMany(mappedBy = "line", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Zone> zones = new ArrayList<>();

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  private Department department;

  public void addZone(Zone zone) {
    zones.add(zone);
    zone.setLine(this);
  }

  public void removeZone(Zone zone) {
    zones.remove(zone);
    zone.setLine(null);
  }

}