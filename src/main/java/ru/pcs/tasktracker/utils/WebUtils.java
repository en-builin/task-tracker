package ru.pcs.tasktracker.utils;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 04.12.2021 in project task-tracker
 */
public class WebUtils {

    public static final String URL_ADD_TASK = "/add-task";
    public static final String URL_PROJECTS = "/projects";
    public static final String URL_REPORT = "/report";
    public static final String URL_SIGN_IN = "/sign-in";
    public static final String URL_SIGN_OUT = "/sign-out";
    public static final String URL_SIGN_UP = "/sign-up";
    public static final String URL_TASKS = "/tasks";
    public static final String URL_USERS = "/users";

    public static final String VIEW_ADD_TASK = "add-task";
    public static final String VIEW_INDEX = "index";
    public static final String VIEW_PROJECTS = "projects";
    public static final String VIEW_REPORT = "report";
    public static final String VIEW_SIGN_IN = "sign-in";
    public static final String VIEW_SIGN_UP = "sign-up";
    public static final String VIEW_TASKS = "tasks";
    public static final String VIEW_TASK = "task";
    public static final String VIEW_USERS = "users";

    public static final String[] PERMIT_ALL_URLS = {
            URL_SIGN_IN,
            URL_SIGN_UP,
            "/css/**",
            "/js/**"
    };

    public static final String[] AUTHENTICATED_URLS = {
            "/",
            URL_ADD_TASK,
            URL_TASKS + "/**",
            URL_REPORT
    };

    public static final String[] ADMIN_URLS = {
            URL_USERS,
            URL_PROJECTS
    };

    private WebUtils() {}
}
