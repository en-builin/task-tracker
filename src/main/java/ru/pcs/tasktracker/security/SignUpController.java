package ru.pcs.tasktracker.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pcs.tasktracker.resolvers.WebResolver;

import javax.validation.Valid;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 24.11.2021 in project task-tracker
 */
@RequiredArgsConstructor()
@Controller
@RequestMapping(WebResolver.URL_SIGN_UP)
public class SignUpController {

    private final SignUpService signUpService;

    @GetMapping
    public String getSignUpPage(Authentication authentication, Model model) {

        if (authentication != null) {
            return "redirect:/";
        }

        model.addAttribute("signUpForm", new SignUpForm());
        return "sign-up";
    }

    @PostMapping
    public String signUp(@Valid @ModelAttribute("signUpForm") SignUpForm form, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "sign-up";
        }

        // TODO: ? обработать исключения из signUpService.signUp()
        signUpService.signUp(form);
        return "redirect:/sign-in";
    }

}
