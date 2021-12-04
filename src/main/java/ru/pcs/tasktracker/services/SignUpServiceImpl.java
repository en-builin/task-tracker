package ru.pcs.tasktracker.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.pcs.tasktracker.dto.SignUpForm;
import ru.pcs.tasktracker.model.User;
import ru.pcs.tasktracker.repositories.UsersRepository;
import ru.pcs.tasktracker.resolvers.EmailResolver;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 24.11.2021 in project task-tracker
 */
@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public class SignUpException extends RuntimeException {
        public SignUpException(String msg) {
            super(msg);
        }
    }

    @Override
    public void signUp(SignUpForm form) {

        User user = usersRepository.findById(form.getEmail())
                .orElseThrow(
                        () -> new SignUpException("User not found!"));

        if (user.getInviteToken() == null || !user.getInviteToken().equals(form.getInviteToken())) {
            throw new SignUpException("Wrong invite token!");
        }

        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setInviteToken(null);
        user.setState(User.State.ACTIVE);

        usersRepository.save(user);

        emailService.sendEmail(user.getEmail(), EmailResolver.REGISTERED_SUBJECT, EmailResolver.REGISTERED_BODY);
    }
}
