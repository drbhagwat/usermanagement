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
@RequestMapping("/api/v1/{divisionId}/departments")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DepartmentController {
  @Autowired
  private DepartmentService departmentService;

  @GetMapping()
  public ResponseEntity<List<Department>> getAll(@PathVariable long divisionId) {
    return ResponseEntity.ok().body(departmentService.getAll(divisionId));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Department> get(@PathVariable long id) {
    return ResponseEntity.ok().body(departmentService.find(id));
  }

  @PostMapping()
  public void add(@PathVariable long divisionId,
                        @Valid @RequestBody Department newDepartment) {
    departmentService.add(divisionId, newDepartment);
  }

  @PutMapping("/{id}")
  public Department update(@PathVariable long id, @Valid @RequestBody Department newDepartment) {
    return departmentService.update(id, newDepartment);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable long id) {
    departmentService.delete(id);
  }
}