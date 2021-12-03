package ru.pcs.tasktracker.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pcs.tasktracker.dto.ProjectDto;
import ru.pcs.tasktracker.services.ProjectsService;
import ru.pcs.tasktracker.services.UsersService;

import javax.validation.Valid;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 01.12.2021 in project task-tracker
 */
@Controller
@RequestMapping("/projects")
@RequiredArgsConstructor()
public class ProjectsController {

    private final ProjectsService projectsService;
    private final UsersService usersService;

    @GetMapping
    public String getProjectsPage(Authentication authentication, Model model) {
        model.addAttribute("projectDto", new ProjectDto());
        model.addAttribute("currentUser", usersService.getUserByEmail(authentication.getName()));
        model.addAttribute("projects", projectsService.getAllProjects());
        return "projects";
    }

    @PostMapping()
    public String addProject(Authentication authentication, @Valid @ModelAttribute("projectDto") ProjectDto projectDto,
                             BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("currentUser", usersService.getUserByEmail(authentication.getName()));
            model.addAttribute("projects", projectsService.getAllProjects());
            return "projects";
        }
        projectsService.addProject(projectDto);
        return "redirect:/projects";
    }
}
