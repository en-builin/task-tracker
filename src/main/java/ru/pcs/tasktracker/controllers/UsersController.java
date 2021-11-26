package ru.pcs.tasktracker.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pcs.tasktracker.dto.InviteForm;
import ru.pcs.tasktracker.services.UsersService;

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
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    @GetMapping
    public String getUsersPage(Authentication authentication, Model model) {
        model.addAttribute("inviteForm", new InviteForm());
        // TODO как вывести полное имя пользователя?
        model.addAttribute("currentUser", authentication.getName());
        model.addAttribute("users", usersService.getAllUsers());
        return "users";
    }

    @PostMapping()
    public String invite(Authentication authentication, @Valid InviteForm inviteform, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("inviteForm", inviteform);
            model.addAttribute("currentUser", authentication.getName());
            model.addAttribute("users", usersService.getAllUsers());
            return "users";
        }
        usersService.invite(inviteform);
        return "redirect:/users";
    }
}
