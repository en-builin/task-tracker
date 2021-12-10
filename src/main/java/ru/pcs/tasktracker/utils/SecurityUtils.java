package ru.pcs.tasktracker.utils;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 04.12.2021 in project task-tracker
 */
public class SecurityUtils {

    public static final String REMEMBER_ME_KEY = "a52378957b9579d56e827ac3e4a0281f";
    public static final String REMEMBER_ME_PARAMETER = "remember-me";
    public static final int TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 365;
    public static final String USERNAME_PARAMETER = "email";
    public static final String PASSWORD_PARAMETER = "password";

    private SecurityUtils() {}
}
