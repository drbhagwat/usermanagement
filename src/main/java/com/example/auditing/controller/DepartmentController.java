package com.example.auditing.controller;

import com.example.auditing.model.Department;
import com.example.auditing.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DepartmentController {
  private final DepartmentService departmentService;

  @GetMapping("/api/v1/{divisionId}/departments")
  public ResponseEntity<List<Department>> getAll(@PathVariable long divisionId) {
    return ResponseEntity.ok().body(departmentService.getAll(divisionId));
  }

  @GetMapping("/api/v1/departments/{id}")
  public ResponseEntity<Department> get(@PathVariable long id) {
    return ResponseEntity.ok().body(departmentService.find(id));
  }

  @PostMapping("/api/v1/{divisionId}/departments")
  public void add(@PathVariable long divisionId,
                  @Valid @RequestBody Department newDepartment) {
    departmentService.add(divisionId, newDepartment);
  }

  @PutMapping("/api/v1/departments/{id}")
  public Department update(@PathVariable long id,
                           @Valid @RequestBody Department newDepartment) {
    return departmentService.update(id, newDepartment);
  }

  @DeleteMapping("/api/v1/departments/{id}")
  public void delete(@PathVariable long id) {
    departmentService.delete(id);
  }
}