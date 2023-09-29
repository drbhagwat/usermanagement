package com.example.auditing.model;

import com.example.auditing.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken extends Auditable<String> implements Serializable {
  @Id
  @GeneratedValue
  private long id;

  private String token;

  private Instant expiryDate;

  @OneToOne
  @JoinColumn(name="user_id", referencedColumnName = "id")
  private User user;
}
