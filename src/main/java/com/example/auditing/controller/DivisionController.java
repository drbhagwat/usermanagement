package com.example.auditing.controller;

import com.example.auditing.model.Division;
import com.example.auditing.service.DivisionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/api/v1/divisions")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DivisionController {
  private final DivisionService divisionService;

  @GetMapping()
  public ResponseEntity<List<Division>> getAll(Model model) {
    return ResponseEntity.ok().body(divisionService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Division> get(@PathVariable long id) {
    return ResponseEntity.ok().body(divisionService.find(id));
  }

  @PostMapping()
  public Division add(@Valid @RequestBody Division newDivision) {
    return (divisionService.add(newDivision));
  }

  @PutMapping("/{id}")
  public Division update(@PathVariable long id, @Valid @RequestBody Division newDivision) {
    return divisionService.update(id, newDivision);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable long id) {
    divisionService.delete(id);
  }
}