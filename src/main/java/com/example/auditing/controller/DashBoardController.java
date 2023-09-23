package com.example.auditing.controller;

import com.example.auditing.model.DashboardDTO;
import com.example.auditing.service.DashboardDTOService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DashBoardController {
  private final DashboardDTOService dashboardDTOService;

  @GetMapping()
  public ResponseEntity<DashboardDTO> get() {
    return ResponseEntity.ok().body(dashboardDTOService.get());
  }
}