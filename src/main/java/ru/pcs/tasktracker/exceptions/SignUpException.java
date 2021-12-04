package ru.pcs.tasktracker.exceptions;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 04.12.2021 in project task-tracker
 */
public class SignUpException extends RuntimeException {
    public SignUpException(String msg) {
        super(msg);
    }
}
