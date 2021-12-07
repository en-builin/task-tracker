package ru.pcs.tasktracker;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ru.pcs.tasktracker.config.security.details.UserDetailsImpl;
import ru.pcs.tasktracker.model.User;

import java.util.Arrays;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 07.12.2021 in project task-tracker
 */
@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User basicUser = User.builder()
                .email("user@company.com")
                .name("User")
                .password("password")
                .role(User.Role.USER)
                .state(User.State.ACTIVE)
                .build();

        User adminUser = User.builder()
                .email("admin@company.com")
                .name("Admin")
                .password("password")
                .role(User.Role.ADMIN)
                .state(User.State.ACTIVE)
                .build();

        return new InMemoryUserDetailsManager(Arrays.asList(
                new UserDetailsImpl(basicUser),
                new UserDetailsImpl(adminUser)
        ));
    }
}