package ru.pcs.tasktracker.notifications;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 04.12.2021 in project task-tracker
 */
public interface EmailService {

    void sendEmail(String receiverEmail, String subject, String body);
}
