package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.AdminServiceCRUD;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {


    private final RoleRepository roleRepository;
    private final AdminServiceCRUD adminServiceCRUD;

    @Autowired
    public AdminController(RoleRepository roleRepository, AdminServiceCRUD adminServiceCRUD) {

        this.roleRepository = roleRepository;
        this.adminServiceCRUD = adminServiceCRUD;
    }

    @GetMapping("")
    public String adminInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        model.addAttribute("authorities", authorities);
        return "user";
    }

    @GetMapping("/all")
    public String showAllUsers(Model model) {
        model.addAttribute("users", adminServiceCRUD. getListAllUsers());
        return "all_users";
    }

    @GetMapping("/new")
    public String createNewUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "create_user";
    }

    @PostMapping("/create")
    public String saveNewUser(@ModelAttribute("user") @Valid User user,
                              BindingResult bindingResult,
                              @RequestParam(value = "roles1", required = false) List<String> roleName,
                              Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAll());
            return "create_user";
        }

        boolean isUserSaved;
        if (roleName == null) {
            isUserSaved = adminServiceCRUD.saveUser(user);
        } else {
            isUserSaved = adminServiceCRUD.saveUser(user,roleName);
        }

        if (isUserSaved) {
            return "redirect:/admin/all";
        } else {
            model.addAttribute("roles", roleRepository.findAll());
            model.addAttribute("errorMessage", "User is already exists.");
            return "create_user";
        }
    }

    @GetMapping("/{id}/edit")
    public String updateUser(Model model,
                             @PathVariable("id") int id) {
        model.addAttribute("user", adminServiceCRUD.findUserById(id));
        model.addAttribute("roles", roleRepository.findAll());
        return "update_user";
    }

    @PostMapping("/update")
    public String saveUpdateUser(@ModelAttribute("user") @Valid User user,
                                 BindingResult bindingResult,
                                 @RequestParam(value = "rolesFromView", required = false) List<String> rolesFromView,
                                 Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAll());
            return "update_user";
        }
        adminServiceCRUD.updateUser(user, rolesFromView);
        return "redirect:/admin/all";
    }

    @PostMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id,
                             @ModelAttribute("user") User user) {
        adminServiceCRUD.deleteUser(id);
        return "redirect:/admin/all";
    }
}


