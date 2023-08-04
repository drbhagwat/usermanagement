package com.example.auditing.service;

import com.example.auditing.exception.ResourceNotFoundException;
import com.example.auditing.model.Department;
import com.example.auditing.model.Division;
import com.example.auditing.repository.DepartmentRepository;
import com.example.auditing.repository.DivisionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DepartmentService {
  private final DivisionService divisionService;
  private final DepartmentRepository departmentRepository;
  private final DivisionRepository divisionRepository;

  public Department find(long id) {
    return departmentRepository.findById(id).orElseThrow(() ->
        new ResourceNotFoundException("Department with id " + id + " Not Found!"));
  }

  public List<Department> getAll(long divisionId) {
    return departmentRepository.findDepartmentsByDivisionId(divisionId);
  }

  public void add(long divisionId, Department department) {
    Division division = divisionService.find(divisionId);
    division.addDepartment(department);
    divisionService.add(division);
  }

  @Transactional
  public Department update(long id, Department newDepartment) {
    Department existingDepartment = find(id);
    existingDepartment.setName(newDepartment.getName());
    return departmentRepository.save(existingDepartment);
  }

  @Transactional
  public void delete(long id) {
    Department existingDepartment = find(id);
    departmentRepository.delete(existingDepartment);
  }
}