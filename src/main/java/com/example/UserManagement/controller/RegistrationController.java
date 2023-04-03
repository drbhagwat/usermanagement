package com.example.UserManagement.controller;

import com.example.UserManagement.enums.UserRole;
import com.example.UserManagement.model.User;
import com.example.UserManagement.service.UserService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Data
@RequestMapping("/register")
public class RegistrationController {
  private final UserService userService;

  @GetMapping
  public String register(Model model) {
    int numberOfUsers = userService.getNumberOfUsers();
    model.addAttribute("numberOfUsers", numberOfUsers);
    model.addAttribute("user", new User());

    /*
      dropdown-list of roles of all types of users in the system is populated
      (but, only for the second and later users; The first user is
      automatically registered as an admin). As of now, the two roles are:
      ADMIN, and USER. This could be extended to other roles. The system
      enforces the creation of the first user as ADMIN */
    if (numberOfUsers != 0) {
      model.addAttribute("roleNames", UserRole.values());
    }
    return "register";
  }

  @PostMapping
  public String register(@Valid User user, BindingResult bindingResult,
                         Model model) {
    int numberOfUsers = userService.getNumberOfUsers();
    model.addAttribute("numberOfUsers", userService.getNumberOfUsers());
    model.addAttribute("user", user);

    if (numberOfUsers != 0) {
      model.addAttribute("roleNames", UserRole.values());
    }

    if (bindingResult.hasErrors()) {
      return "register";
    }
    boolean isUserSuccessfullyAdded = userService.add(user);
    return isUserSuccessfullyAdded ? "redirect:/register?success" : "redirect" +
        ":/register?emailExists";
  }
}
