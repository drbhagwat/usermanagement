package com.example.auditing.service;

import com.example.auditing.exception.ResourceNotFoundException;
import com.example.auditing.model.Department;
import com.example.auditing.model.Line;
import com.example.auditing.repository.LineRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LineService {
  private final DepartmentService departmentService;
  private final LineRepository lineRepository;

  public Line find(long id) {
    return lineRepository.findById(id).orElseThrow(() ->
        new ResourceNotFoundException("Line with id " + id + " Not Found!"));
  }

  public List<Line> getAll(long departmentId) {
    return lineRepository.findLinesByDepartmentId(departmentId);
  }

  @Transactional
  public void add(long departmentId, Line newLine) {
    Department department = departmentService.find(departmentId);
    department.addLine(newLine);
  }

  @Transactional
  public Line update(long id, Line newLine) {
    Line existingLine = find(id);
    existingLine.setName(newLine.getName());
    return lineRepository.save(existingLine);
  }

  @Transactional
  public void delete(long id) {
    Line existingLine = find(id);
    lineRepository.delete(existingLine);
  }
}