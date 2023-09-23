package com.example.auditing.service;

import com.example.auditing.exception.ResourceNotFoundException;
import com.example.auditing.model.Line;
import com.example.auditing.model.Zone;
import com.example.auditing.repository.ZoneRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ZoneService {
  private final ZoneRepository zoneRepository;
  private final LineService lineService;

  public Zone find(long id) {
    return zoneRepository.findById(id).orElseThrow(() ->
        new ResourceNotFoundException("Zone with id " + id + " Not Found!"));
  }

  public List<Zone> getAll(long lineId) {
    return zoneRepository.findZonesByLineId(lineId);
  }

  public List<Zone> getAll() {
    return zoneRepository.findAll();
  }

  @Transactional
  public void add(long lineId, Zone newZone) {
    Line line = lineService.find(lineId);
    line.addZone(newZone);
  }

  @Transactional
  public Zone update(long id, Zone newZone) {
    Zone existingZone = find(id);
    existingZone.setName(newZone.getName());
    return zoneRepository.save(existingZone);
  }

  @Transactional
  public void delete(long id) {
    Zone existingZone = find(id);
    zoneRepository.delete(existingZone);
  }
}