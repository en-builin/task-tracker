package ru.pcs.tasktracker;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ru.pcs.tasktracker.security.config.UserDetailsImpl;

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

        return new InMemoryUserDetailsManager(Arrays.asList(
                new UserDetailsImpl(TestUsersUtils.getBasicUser()),
                new UserDetailsImpl(TestUsersUtils.getAdminUser())
        ));
    }
}