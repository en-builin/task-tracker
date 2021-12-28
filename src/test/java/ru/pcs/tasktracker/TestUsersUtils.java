package ru.pcs.tasktracker;

import ru.pcs.tasktracker.users.User;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 08.12.2021 in project task-tracker
 */
public class TestUsersUtils {

    public static User getBasicUser() {
        return User.builder()
                .email("user@company.com")
                .name("User")
                .password("password")
                .role(User.Role.USER)
                .state(User.State.ACTIVE)
                .build();
    }

    public static User getAdminUser() {
        return User.builder()
                .email("admin@company.com")
                .name("Admin")
                .password("password")
                .role(User.Role.ADMIN)
                .state(User.State.ACTIVE)
                .build();
    }
}
