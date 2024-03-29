package com.example.auditing.controller;

import com.example.auditing.model.User;
import com.example.auditing.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
  private final UserService userService;

  @GetMapping()
  public ResponseEntity<List<User>> getAll(Model model) {
    return ResponseEntity.ok().body(userService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> get(@PathVariable long id) {
    return ResponseEntity.ok().body(userService.find(id));
  }

  @PostMapping("/register")
  public User add(@Valid @RequestBody User newUser) {
    return (userService.add(newUser));
  }

  @PutMapping("/{id}")
  public User update(@PathVariable long id, @Valid @RequestBody User newUser) {
    return userService.update(id, newUser);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable long id) {
    userService.delete(id);
  }
}