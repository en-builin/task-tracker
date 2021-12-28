package ru.pcs.tasktracker.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pcs.tasktracker.security.InviteForm;
import ru.pcs.tasktracker.notifications.EmailService;
import ru.pcs.tasktracker.security.exceptions.UserNotFoundException;
import ru.pcs.tasktracker.resolvers.EmailResolver;

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

    private final UsersRepository usersRepository;
    private final EmailService emailService;

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

        emailService.sendEmail(user.getEmail(),
                EmailResolver.INVITED_SUBJECT, EmailResolver.INVITED_BODY + user.getInviteToken());
    }

    @Override
    public List<UserDto> getAllUsers() {
        return UserDto.from(usersRepository.findByOrderByNameAsc());
    }

    public List<UserDto> getActiveUsers() {
        return UserDto.from(usersRepository.findByStateIsOrderByNameAsc(User.State.ACTIVE));
    }

    @Override
    public User getUserByEmail(String email) {
        return usersRepository.findById(email).orElseThrow(UserNotFoundException::new);
    }
}
