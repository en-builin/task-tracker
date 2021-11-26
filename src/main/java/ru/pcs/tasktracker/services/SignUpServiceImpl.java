package ru.pcs.tasktracker.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.pcs.tasktracker.dto.SignUpForm;
import ru.pcs.tasktracker.model.User;
import ru.pcs.tasktracker.repositories.UsersRepository;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 24.11.2021 in project task-tracker
 */
@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

//    private final JavaMailSender mailSender;
//
//    @Value("${spring.mail.username}")
//    private String from;

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(SignUpForm form) {

        User user = usersRepository.findById(form.getEmail())
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found!"));

        if (!user.getInviteToken().equals(form.getInviteToken())) {
            throw new UsernameNotFoundException("Wrong invite token!");
        }

        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setInviteToken(null);
        user.setState(User.State.ACTIVE);

//        sendMail("<h1>Вы зарегистрированы</h1>", "Регистрация", from, user.getEmail());

        usersRepository.save(user);
    }

//    private void sendMail(String text, String subject, String from, String to) {
//        MimeMessagePreparator preparator = mimeMessage -> {
//          MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
//          messageHelper.setText(text, true);
//          messageHelper.setTo(to);
//          messageHelper.setFrom(from);
//          messageHelper.setSubject(subject);
//        };
//
//        mailSender.send(preparator);
//    }
}
