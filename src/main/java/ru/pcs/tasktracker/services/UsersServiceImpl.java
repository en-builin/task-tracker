package ru.pcs.tasktracker.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pcs.tasktracker.dto.InviteForm;
import ru.pcs.tasktracker.dto.UserDto;
import ru.pcs.tasktracker.model.User;
import ru.pcs.tasktracker.repositories.UsersRepository;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 25.11.2021 in project task-tracker
 */
@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    public static class UserNotFoundException extends RuntimeException {}

    private final UsersRepository usersRepository;

    @Override
    public void invite(InviteForm inviteForm) {

        User user = User.builder()
                .name(inviteForm.getName())
                .email(inviteForm.getEmail().toLowerCase(Locale.ROOT))
                .role(User.Role.USER)
                .state(User.State.INVITED)
                .inviteToken(UUID.randomUUID().toString())
                .build();

        usersRepository.save(user);

        // TODO send email to user with invite token

    }

    @Override
    public List<UserDto> getAllUsers() {
        return UserDto.from(usersRepository.findByOrderByNameAsc());
    }

    @Override
    public String getUserNameByEmail(String email) {
        return usersRepository.findById(email).map(User::getName).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User getUserByEmail(String email) {
        return usersRepository.findById(email).orElseThrow(UserNotFoundException::new);
    }
}
