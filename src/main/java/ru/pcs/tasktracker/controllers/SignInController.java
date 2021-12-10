package ru.pcs.tasktracker.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pcs.tasktracker.utils.WebUtils;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 24.11.2021 in project task-tracker
 */
@Controller
@RequestMapping(WebUtils.URL_SIGN_IN)
public class SignInController {

    @GetMapping
    public String getSignInPage(Authentication authentication) {
        return WebUtils.VIEW_SIGN_IN;
    }
}
