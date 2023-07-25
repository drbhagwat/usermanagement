package com.example.auditing.repository;

import com.example.auditing.model.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DivisionRepository extends JpaRepository<Division, Long> {
}
