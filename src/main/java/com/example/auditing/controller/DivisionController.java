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
@RequestMapping("/api/v1/divisions")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DivisionController {
  @Autowired
  private DivisionService divisionService;

  @GetMapping()
  public ResponseEntity<List<Division>> getAll(Model model) {
    return ResponseEntity.ok().body(divisionService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Division> get(@PathVariable long id) {
    return ResponseEntity.ok().body(divisionService.find(id));
  }

  @PostMapping()
  public Division add(@Valid @RequestBody Division newUser) {
    return (divisionService.add(newUser));
  }

  @PutMapping("/{id}")
  public Division update(@PathVariable long id, @Valid @RequestBody Division newUser) {
    return divisionService.update(id, newUser);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable long id) {
    divisionService.delete(id);
  }
}