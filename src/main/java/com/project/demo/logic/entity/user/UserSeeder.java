package com.project.demo.logic.entity.user;

import com.project.demo.logic.entity.rol.Role;
import com.project.demo.logic.entity.rol.RoleEnum;
import com.project.demo.logic.entity.rol.RoleRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class UserSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserSeeder(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.setUsers();
    }

    private void setUsers(){
        Optional<Role> superAdminRoleOptional = roleRepository.findByName(RoleEnum.SUPER_ADMIN);
        Optional<Role> userRoleOptional = roleRepository.findByName(RoleEnum.USER);

        // Check if roles are present
        if (superAdminRoleOptional.isEmpty() || userRoleOptional.isEmpty()) {
            throw new RuntimeException("Required roles not found in the database");
        }

        // Get role objects from Optionals
        Role superAdminRole = superAdminRoleOptional.get();
        Role userRole = userRoleOptional.get();
        List<User> userList = Arrays.asList(
                new User("Juan", "Martinez", "juan@gmail.com", passwordEncoder.encode("Cenfotec123!"), new Date(), new Date(), superAdminRole),
                new User("Juan", "Martinez", "juanmi@gmail.com", passwordEncoder.encode("Cenfotec123!"), new Date(), new Date(), userRole)
        );

        userList.forEach(user -> {
            Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

            optionalUser.ifPresentOrElse(System.out::println, () -> {
                userRepository.save(user);
            });
        });
    }
}
