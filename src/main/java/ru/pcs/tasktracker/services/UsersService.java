package ru.pcs.tasktracker.services;

import ru.pcs.tasktracker.dto.InviteForm;
import ru.pcs.tasktracker.dto.UserDto;

import java.util.List;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 25.11.2021 in project task-tracker
 */
public interface UsersService {
    void invite(InviteForm inviteForm);

    List<UserDto> getAllUsers();
}
