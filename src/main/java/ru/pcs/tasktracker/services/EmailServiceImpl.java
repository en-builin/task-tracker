package ru.pcs.tasktracker.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import ru.pcs.tasktracker.utils.EmailUtils;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 04.12.2021 in project task-tracker
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    public void sendEmail(String receiverEmail, String subject, String htmlBody) {

        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(senderEmail, EmailUtils.SENDER_NAME);
            messageHelper.setTo(receiverEmail);
            messageHelper.setSubject(subject);
            messageHelper.setText(htmlBody, true);
        };

        try {
            mailSender.send(mimeMessagePreparator);
        } catch (MailException e) {
            log.error("Unsuccessful attempt to send mail.", e);
            throw new IllegalStateException(e);
        }
    }
}
