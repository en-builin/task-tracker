package ru.pcs.tasktracker.utils;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 04.12.2021 in project task-tracker
 */
public class WebUtils {

    public static final String URL_SIGN_IN = "/sign-in";
    public static final String URL_SIGN_OUT = "/sign-out";
    public static final String URL_SIGN_UP = "/sign-up";

    public static final String[] PERMIT_ALL_URLS = {
            URL_SIGN_IN,
            URL_SIGN_UP,
            "/css/**",
            "/js/**"
    };

    public static final String[] AUTHENTICATED_URLS = {
            "/",
            "/add-task",
            "/tasks/**",
            "/report"
    };

    public static final String[] ADMIN_URLS = {
            "/users",
            "/projects"
    };

    private WebUtils() {}
}
