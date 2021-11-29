package ru.pcs.tasktracker.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pcs.tasktracker.dto.SignUpForm;
import ru.pcs.tasktracker.services.SignUpService;

import javax.validation.Valid;

/**
 * Контроллер регистрации новых пользователей. Контроллер доступен только при установке
 * app.allow-sign-up=true, по умолчанию контроллер отключен. Нужен для первичной регистрации
 * администратора системы. Дальнейшие регистрации происходят только по приглашениям.
 *
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 24.11.2021 in project task-tracker
 */
@RequiredArgsConstructor()
@Controller
@RequestMapping("/sign-up")
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
    public String signUp(@Valid SignUpForm form, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("signUpForm", form);
            return "sign-up";
        }
        // TODO: ? обработать исключения из signUpService.signUp()
        signUpService.signUp(form);
        return "redirect:/sign-in";
    }

}
