package com.example.auditing.repository;

import com.example.auditing.model.Line;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineRepository extends JpaRepository<Line, Long> {
  @Query("SELECT l FROM Line l JOIN l.department dep " +
      "WHERE dep.id = :departmentId")
  List<Line> findLinesByDepartmentId(@Param("departmentId") long departmentId);
}
