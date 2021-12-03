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
import ru.pcs.tasktracker.dto.TaskDto;
import ru.pcs.tasktracker.services.ProjectsService;
import ru.pcs.tasktracker.services.TasksService;

import javax.validation.Valid;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 29.11.2021 in project task-tracker
 */
@RequiredArgsConstructor()
@Controller
@RequestMapping("/add-task")
public class AddTaskController {

    private final TasksService tasksService;
    private final ProjectsService projectsService;

    @GetMapping
    public String getAddTaskForm(Model model) {
        model.addAttribute("task", new TaskDto());
        model.addAttribute("projects", projectsService.getAllProjects());
        return "add-task";
    }

    @PostMapping
    public String addTask(Authentication authentication, @Valid @ModelAttribute("task") TaskDto task, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "add-task";
        }

        task.setAuthorEmail(authentication.getName());

        tasksService.addTask(task);

        return "redirect:/tasks";
    }

}
