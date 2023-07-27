package com.example.auditing.controller;

import com.example.auditing.model.Line;
import com.example.auditing.service.LineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LineController {
  @Autowired
  private LineService lineService;

  @GetMapping("/api/v1/{departmentId}/lines")
  public ResponseEntity<List<Line>> getAll(@PathVariable long departmentId) {
    return ResponseEntity.ok().body(lineService.getAll(departmentId));
  }

  @GetMapping("/api/v1/lines/{id}")
  public ResponseEntity<Line> get(@PathVariable long id) {
    return ResponseEntity.ok().body(lineService.find(id));
  }

  @PostMapping("/api/v1/{departmentId}/lines")
  public void add(@PathVariable long departmentId,
                  @Valid @RequestBody Line newLine) {
    lineService.add(departmentId, newLine);
  }

  @PutMapping("/api/v1/lines/{id}")
  public Line update(@PathVariable long id,
                           @Valid @RequestBody Line newLine) {
    return lineService.update(id, newLine);
  }

  @DeleteMapping("/api/v1/lines/{id}")
  public void delete(@PathVariable long id) {
    lineService.delete(id);
  }
}