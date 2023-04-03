package com.example.UserManagement.controller;

import com.example.UserManagement.enums.UserRole;
import com.example.UserManagement.model.User;
import com.example.UserManagement.service.UserService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/api/v1/users")
@Data
public class UserController {
  @Autowired
  private UserService userService;

  @GetMapping
  public String getAll(Model model) {
    model.addAttribute("loggedInUser", userService.getLoggedInUser());
    model.addAttribute("users", userService.getAll());
    return "users";
  }

  @GetMapping("get")
  public ModelAndView get(@RequestParam String userName) {
    ModelAndView modelAndView = new ModelAndView("user");
    modelAndView.addObject("user", userService.get(userName));
    return modelAndView;
  }

  @GetMapping("add")
  public String add(Model model) {
    model.addAttribute("loggedInUser", userService.getLoggedInUser());
    model.addAttribute("user", new User());
    return "/register";
  }

  @GetMapping("update")
  public String update(@RequestParam String userName, Model model) {
    User existingUser = userService.find(userName);

    if (existingUser == null) {
      return "redirect:/api/v1/users?updateFailure";
    }
    model.addAttribute("user", existingUser);
    int numberOfUsers = userService.getNumberOfUsers();
    model.addAttribute("numberOfUsers", numberOfUsers);

      /*
        dropdown-list of users of all types in the system is populated (but,
        only for the second and later users; The first user is always
        automatically registered as an admin). As of now, the two types are:
        ADMIN, and USER. This could be extended to other types of users. The
        system enforces the creation of the very first user as ADMIN */
    if (numberOfUsers != 0) {
      model.addAttribute("roleNames", UserRole.values());
    }
    return "update";
  }

  @PostMapping("update")
  public String update(@Valid User newUser, BindingResult bindingResult,
                       Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("user", newUser);
      return "update";
    }
    User tempUser = userService.update(newUser);

    if (tempUser == null) {
      return "redirect:/api/v1/users?updateFailure";
    }
    return "redirect:/api/v1/users?updateSuccess";
  }

  @GetMapping("delete")
  public String delete(@RequestParam String userName) {
    User deletedUser = userService.delete(userName);

    if (deletedUser == null) {
      return "redirect:/api/v1/users?deleteFailure";
    }

    if (userService.getNumberOfUsers() == 0) {
      return "redirect:/register";
    } else {
      return "redirect:/api/v1/users?deleteSuccess";
    }
  }
}