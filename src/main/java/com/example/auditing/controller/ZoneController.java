package com.example.auditing.controller;

import com.example.auditing.model.Zone;
import com.example.auditing.service.ZoneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ZoneController {
  private final ZoneService zoneService;

  @GetMapping("/api/v1/{lineId}/zones")
  public ResponseEntity<List<Zone>> getAll(@PathVariable long lineId) {
    return ResponseEntity.ok().body(zoneService.getAll(lineId));
  }

  @GetMapping("/api/v1/zones/{id}")
  public ResponseEntity<Zone> get(@PathVariable long id) {
    return ResponseEntity.ok().body(zoneService.find(id));
  }

  @PostMapping("/api/v1/{lineId}/zones")
  public void add(@PathVariable long lineId, @Valid @RequestBody Zone newZone) {
    zoneService.add(lineId, newZone);
  }

  @PutMapping("/api/v1/zones/{id}")
  public Zone update(@PathVariable long id, @Valid @RequestBody Zone newZone) {
    return zoneService.update(id, newZone);
  }

  @DeleteMapping("/api/v1/zones/{id}")
  public void delete(@PathVariable long id) {
    zoneService.delete(id);
  }
}