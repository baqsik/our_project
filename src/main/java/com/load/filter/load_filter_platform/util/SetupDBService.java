package com.load.filter.load_filter_platform.util;

import com.load.filter.load_filter_platform.model.entity.Role;
import com.load.filter.load_filter_platform.model.entity.User;
import com.load.filter.load_filter_platform.repository.RoleRepository;
import com.load.filter.load_filter_platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SetupDBService {

    private static final Logger LOGGER = LogManager.getLogger(SetupDBService.class);

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;


    public void setup() {
        LOGGER.info("Setup started");
        Role role = new Role();
        role.setName(RoleName.ADMIN);
        roleRepository.save(role);


        User user = new User();
        user.setPassword(passwordEncoder.encode("password"));
        createUser(user);
        userRepository.save(user);
        LOGGER.info("Setup ended");
    }

    private void createUser(User user) {

        Role role = new Role();
        role.setName(RoleName.ADMIN);
        user.setName("Ashot");
        user.setUsername("Ashot");
        user.setRole(role);
    }
}
