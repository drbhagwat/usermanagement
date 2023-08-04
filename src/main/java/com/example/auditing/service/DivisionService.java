package com.example.auditing.service;

import com.example.auditing.exception.ResourceNotFoundException;
import com.example.auditing.model.Division;
import com.example.auditing.repository.DivisionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DivisionService {
  private final DivisionRepository divisionRepository;

  public Division find(long id) {
    return divisionRepository.findById(id).orElseThrow(() ->
        new ResourceNotFoundException("Division with id " + id + " Not Found!"));
  }

  public List<Division> getAll() {
    return divisionRepository.findAll();
  }

  public Division add(Division division) {
    return divisionRepository.save(division);
  }

  @Transactional
  public Division update(long id, Division newDivision) {
    Division existingDivision = find(id);
    existingDivision.setName(newDivision.getName());
    return divisionRepository.save(existingDivision);
  }

  @Transactional
  public void delete(long id) {
    Division existingDivision = find(id);
    divisionRepository.delete(existingDivision);
  }
}