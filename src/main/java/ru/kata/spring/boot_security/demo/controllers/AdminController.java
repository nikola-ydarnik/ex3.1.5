package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleSerivce;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {


    private final RoleSerivce roleSerivce;
    private final UserService userService;

    @Autowired
    public AdminController(RoleSerivce roleSerivce, UserService userService) {
        this.roleSerivce = roleSerivce;
        this.userService = userService;
    }

    @GetMapping()
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService. getListAllUsers());
        return "all_users";
    }

    @GetMapping("/new")
    public String createNewUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleSerivce.findAll());
        return "create_user";
    }

    @PostMapping("/create")
    public String saveNewUser(@ModelAttribute("user") @Valid User user,
                              BindingResult bindingResult,
                              @RequestParam(value = "roles1", required = false) List<String> roleName,
                              Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleSerivce.findAll());
            return "create_user";
        }

        boolean isUserSaved;
        if (roleName == null) {
            isUserSaved = userService.saveUser(user);
        } else {
            isUserSaved = userService.saveUser(user,roleName);
        }

        if (isUserSaved) {
            return "redirect:/admin/all";
        } else {
            model.addAttribute("roles", roleSerivce.findAll());
            model.addAttribute("errorMessage", "User is already exists.");
            return "create_user";
        }
    }

    @GetMapping("/{id}/edit")
    public String updateUser(Model model,
                             @PathVariable("id") int id) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("roles", roleSerivce.findAll());
        return "update_user";
    }

    @PostMapping("/update")
    public String saveUpdateUser(@ModelAttribute("user") @Valid User user,
                                 BindingResult bindingResult,
                                 @RequestParam(value = "rolesFromView", required = false) List<String> rolesFromView,
                                 Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleSerivce.findAll());
            return "update_user";
        }
        userService.updateUser(user, rolesFromView);
        return "redirect:/admin/all";
    }

    @PostMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id,
                             @ModelAttribute("user") User user) {
        userService.deleteUser(id);
        return "redirect:/admin/all";
    }
}


