package ru.kata.spring.boot_security.demo.util;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleSerivce;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Util {

    private final UserService userService;
    private final RoleSerivce roleSerivce;

    @Autowired
    public Util(UserService userService, RoleSerivce roleSerivce) {
        this.userService = userService;
        this.roleSerivce = roleSerivce;
    }

    @PostConstruct
    public void init() {
        // создаю роли
        Role roleUser = new Role(1, "ROLE_USER");
        Role roleAdmin = new Role(2, "ROLE_ADMIN");
        roleSerivce.addRole(roleUser);
        roleSerivce.addRole(roleAdmin);

        // для входа
        userService.saveUser(new User("user", "user", "userovich", 27, "user@mail.com"),
                new HashSet<Role>(Set.of(roleAdmin, roleUser)));
        userService.saveUser(new User("user2", "user2", "usereovich2", 28, "user2@mail.com"),
                new HashSet<Role>(Set.of(roleUser)));
//        userService.saveUser(new User("Mari", "200", "Tikhonova", 26, "user@mail.com"));
//        userService.saveUser(new User("Misha","300", "Voloshkin", 34, "user@mail.com"));
    }
}
