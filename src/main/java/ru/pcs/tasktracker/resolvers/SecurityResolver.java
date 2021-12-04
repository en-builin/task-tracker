package ru.pcs.tasktracker.resolvers;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 04.12.2021 in project task-tracker
 */
public class SecurityResolver {

    public static final String REMEMBER_ME_KEY = "a52378957b9579d56e827ac3e4a0281f";
    public static final String REMEMBER_ME_PARAMETER = "remember-me";
    public static final int TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 365;

    private SecurityResolver() {}
}
