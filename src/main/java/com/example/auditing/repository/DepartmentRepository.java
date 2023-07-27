package com.example.auditing.repository;

import com.example.auditing.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
  @Query("SELECT d FROM Department d JOIN d.division div " +
      "WHERE div.id = :divisionId")
  List<Department> findDepartmentsByDivisionId(@Param("divisionId") long divisionId);
}
