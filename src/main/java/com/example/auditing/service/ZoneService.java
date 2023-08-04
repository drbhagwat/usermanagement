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
  private final LineService lineService;
  private final ZoneRepository lineRepository;

  public Zone find(long id) {
    return lineRepository.findById(id).orElseThrow(() ->
        new ResourceNotFoundException("Zone with id " + id + " Not Found!"));
  }

  public List<Zone> getAll(long lineId) {
    return lineRepository.findZonesByLineId(lineId);
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
    return lineRepository.save(existingZone);
  }

  @Transactional
  public void delete(long id) {
    Zone existingZone = find(id);
    lineRepository.delete(existingZone);
  }
}