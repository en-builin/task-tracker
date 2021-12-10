package ru.pcs.tasktracker.users;

import ru.pcs.tasktracker.security.InviteForm;

import java.util.List;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 25.11.2021 in project task-tracker
 */
public interface UsersService {

    void invite(InviteForm inviteForm);

    List<UserDto> getAllUsers();

    List<UserDto> getActiveUsers();

    User getUserByEmail(String email);
}
