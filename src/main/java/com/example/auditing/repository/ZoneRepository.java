package com.example.auditing.repository;

import com.example.auditing.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {
  @Query("SELECT z FROM Zone z JOIN z.line l " +
      "WHERE l.id = :lineId")
  List<Zone> findZonesByLineId(@Param("lineId") long lineId);
}
