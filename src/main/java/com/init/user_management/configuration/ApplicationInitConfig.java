package com.init.user_management.configuration;

import com.init.user_management.entity.Users;
import com.init.user_management.enums.Roles;
import com.init.user_management.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Slf4j
@Configuration
public class ApplicationInitConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUserName("admin").isEmpty()) {
                HashSet<String> roles = new HashSet<>();
                roles.add(Roles.ADMIN.name());

                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

                Users admin = new Users();
                admin.setUserName("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(roles);
                userRepository.save(admin);
                log.warn("admin user has been created with default password: admin123");
            }
        };
    }
}
