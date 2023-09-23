package com.example.auditing.service;

import com.example.auditing.model.DashboardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DashboardDTOService {
  private final DivisionService divisionService;
  private final DepartmentService departmentService;
  private final LineService lineService;
  private final ZoneService zoneService;

  public DashboardDTO get() {
    DashboardDTO dashboardDTO = new DashboardDTO();
    dashboardDTO.setDivisions(divisionService.getAll().size());
    dashboardDTO.setDepartments(departmentService.getAll().size());
    dashboardDTO.setLines(lineService.getAll().size());
    dashboardDTO.setZones(zoneService.getAll().size());
    return dashboardDTO;
  }
}