package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AdminServiceCRUD {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminServiceCRUD(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public boolean saveUser(User user) {
        if (userRepository.findUserByName(user.getName()).isPresent()) {
            return false;
        }
        user.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
    @Transactional
    public boolean saveUser(User user, List<String> rolesFromView) {
        if (userRepository.findUserByName(user.getName()).isPresent()) {
            return false;
        }
        Set<Role> roles = roleRepository.findByRoleNameIn(rolesFromView);
        user.setRoles(roles);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Transactional
    public boolean saveUser(User user, Set<Role> roles) {
        user.setRoles(roles);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Transactional
    public List<User> getListAllUsers() {
        return userRepository.findAll();
    }

    @Transactional()
    public User findUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(new User());
    }
    @Transactional
    public void updateUser(User user, List<String> rolesFromView) {
        User userFromDB = userRepository.findUserById(user.getId());
        userFromDB.setName(user.getName());
        userFromDB.setSurname(user.getSurname());
        userFromDB.setAge(user.getAge());
        userFromDB.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if (rolesFromView == null) {
            user.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));
        } else {
            Set<Role> roles = roleRepository.findByRoleNameIn(rolesFromView);
            userFromDB.setRoles(roles);
        }
    }
    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
