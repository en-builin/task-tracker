package ru.pcs.tasktracker.notifications;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import ru.pcs.tasktracker.resolvers.EmailResolver;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 04.12.2021 in project task-tracker
 */
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    public void sendEmail(String receiverEmail, String subject, String htmlBody) {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(senderEmail, EmailResolver.SENDER_NAME);
            messageHelper.setTo(receiverEmail);
            messageHelper.setSubject(subject);
            messageHelper.setText(htmlBody, true);
        };

        try {
            mailSender.send(mimeMessagePreparator);
        } catch (MailException e) {
            throw new IllegalStateException(e);
        }
    }
}
