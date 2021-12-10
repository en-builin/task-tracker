package ru.pcs.tasktracker.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.pcs.tasktracker.dto.SignUpForm;
import ru.pcs.tasktracker.exceptions.SignUpException;
import ru.pcs.tasktracker.model.User;
import ru.pcs.tasktracker.repositories.UsersRepository;
import ru.pcs.tasktracker.utils.EmailUtils;

import java.util.Optional;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 24.11.2021 in project task-tracker
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SignUpServiceImpl implements SignUpService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public void signUp(SignUpForm form) {

        User user;

        Optional<User> userOptional = usersRepository.findById(form.getEmail());
        if (userOptional.isEmpty()) {
            log.warn("Attempt to sign up with wrong email [Email:{}; Token:{}]", form.getEmail(), form.getInviteToken());
            throw new SignUpException("User not found!");
        } else {
            user = userOptional.get();
        }

        if (user.getInviteToken() == null || !user.getInviteToken().equals(form.getInviteToken())) {
            log.warn("Attempt to sign up with wrong token [Email:{}; Token:{}]", form.getEmail(), form.getInviteToken());
            throw new SignUpException("Wrong invite token!");
        }

        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setInviteToken(null);
        user.setState(User.State.ACTIVE);

        usersRepository.save(user);

        emailService.sendEmail(user.getEmail(), EmailUtils.REGISTERED_SUBJECT, EmailUtils.REGISTERED_BODY);
    }
}
