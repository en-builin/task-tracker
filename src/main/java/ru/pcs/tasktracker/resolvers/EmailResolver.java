package ru.pcs.tasktracker.resolvers;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 04.12.2021 in project task-tracker
 */
public class EmailResolver {

    public static final String SENDER_NAME = "Task Tracker Bot";
    public static final String REGISTERED_SUBJECT = "Registration success";
    public static final String REGISTERED_BODY = "You are registered<br><br>Use your credentials to sign in.";
    public static final String INVITED_SUBJECT = "Your registration invite";
    public static final String INVITED_BODY = "Register to Task Tracker using your email and invitation token<br>" +
            "<a href='localhost/sign-up'>Sign up</a><br><b>TOKEN:</b> ";

    private EmailResolver() {}
}
