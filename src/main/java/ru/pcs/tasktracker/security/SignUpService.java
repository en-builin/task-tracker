package ru.pcs.tasktracker.security;

import ru.pcs.tasktracker.security.SignUpForm;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 24.11.2021 in project task-tracker
 */
public interface SignUpService {

    void signUp(SignUpForm form);
}
