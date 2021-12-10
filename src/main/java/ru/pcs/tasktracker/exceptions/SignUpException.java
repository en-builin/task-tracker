package ru.pcs.tasktracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 04.12.2021 in project task-tracker
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Denied credentials")
public class SignUpException extends RuntimeException {
    public SignUpException(String msg) {
        super(msg);
    }
}
