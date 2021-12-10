package ru.pcs.tasktracker.users;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pcs.tasktracker.security.InviteForm;

import javax.validation.Valid;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 24.11.2021 in project task-tracker
 */
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor()
public class UsersController {

    private final UsersService usersService;

    @GetMapping
    public String getUsersPage(Authentication authentication, Model model) {
        model.addAttribute("inviteForm", new InviteForm());
        model.addAttribute("currentUser", usersService.getUserByEmail(authentication.getName()));
        model.addAttribute("users", usersService.getAllUsers());
        return "users";
    }

    @PostMapping()
    public String invite(Authentication authentication, @Valid @ModelAttribute("inviteForm") InviteForm inviteform, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("currentUser", usersService.getUserByEmail(authentication.getName()));
            model.addAttribute("users", usersService.getAllUsers());
            return "users";
        }
        usersService.invite(inviteform);
        return "redirect:/users";
    }
}
