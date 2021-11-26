package ru.pcs.tasktracker.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 24.11.2021 in project task-tracker
 */
@Controller
@RequestMapping("/")
public class IndexController {
    @GetMapping
    public String getIndexPage(Authentication authentication, Model model) {
        model.addAttribute("currentUser", authentication.getName());
        return "index";
    }
}