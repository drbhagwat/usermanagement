package com.example.auditing.audit;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> {
  @Temporal(TemporalType.TIMESTAMP)
  @CreatedDate
  public Date createdTime;

  @CreatedBy
  public U createdBy;

  @LastModifiedDate
  @Temporal(TemporalType.TIMESTAMP)
  public Date lastModifiedTime;

  @LastModifiedBy
  public U modifiedBy;
}
