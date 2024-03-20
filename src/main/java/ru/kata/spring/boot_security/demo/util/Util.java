package ru.kata.spring.boot_security.demo.util;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.service.AdminServiceCRUD;
import ru.kata.spring.boot_security.demo.service.RoleService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Util {

    private final AdminServiceCRUD adminServiceCRUD;
    private final RoleService roleService;

    @Autowired
    public Util(AdminServiceCRUD adminServiceCRUD, RoleService roleService) {
        this.adminServiceCRUD = adminServiceCRUD;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {
        Role roleUser = new Role(1, "ROLE_USER");
        Role roleAdmin = new Role(2, "ROLE_ADMIN");
        roleService.addRole(roleUser);
        roleService.addRole(roleAdmin);

        // для входа
        adminServiceCRUD.saveUser(new User("user", "user", "userovich", 27),
                new HashSet<Role>(Set.of(roleAdmin, roleUser)));

        adminServiceCRUD.saveUser(new User("Nick", "100", "Tikhonov", 27),
                new HashSet<Role>(Set.of(roleAdmin, roleUser)));
        adminServiceCRUD.saveUser(new User("Mari", "200", "Tikhonova", 26));
        adminServiceCRUD.saveUser(new User("Misha","300", "Voloshkin", 34));
    }
}
