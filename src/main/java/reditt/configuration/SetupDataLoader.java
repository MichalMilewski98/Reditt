package reditt.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reditt.model.Permission;
import reditt.model.Role;
import reditt.model.User;
import reditt.repository.PermissionRepository;
import reditt.repository.RoleRepository;
import reditt.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private boolean isAlreadySetup = false;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SetupDataLoader(UserRepository userRepository, RoleRepository roleRepository,
           PermissionRepository permissionRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (this.isAlreadySetup)
            return;
        Permission readPrivilege
                = createPrivilegeIfNotFound("READ_PERMISSION");
        Permission writePrivilege
                = createPrivilegeIfNotFound("WRITE_PERMISSION");

        List<Permission> adminPrivileges = Arrays.asList(
                readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

        Role adminRole = this.roleRepository.findByName("ROLE_ADMIN");
        User user = new User();
        user.setPassword(passwordEncoder.encode("test"));
        user.setEmail("test@test.com");
        user.setRoles(List.of(adminRole));
        user.setActive(true);
        this.userRepository.save(user);

        this.isAlreadySetup = true;
    }

    @Transactional
    Permission createPrivilegeIfNotFound(String name) {
        Permission privilege = this.permissionRepository.findByName(name);
        if (privilege == null) {
            privilege = new Permission(name);
            this.permissionRepository.save(privilege);
        }

        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(String name, Collection<Permission> privileges) {
        Role role = this.roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPermissions(privileges);
            this.roleRepository.save(role);
        }

        return role;
    }
}
