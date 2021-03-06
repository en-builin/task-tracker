package ru.pcs.tasktracker.tasks;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pcs.tasktracker.projects.ProjectsService;
import ru.pcs.tasktracker.users.UsersService;

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
    private final UsersService usersService;

    @GetMapping
    public String getAddTaskForm(Model model) {
        model.addAttribute("task", new TaskDto());
        model.addAttribute("projects", projectsService.getAllProjects());
        model.addAttribute("users", usersService.getActiveUsers());
        return "add-task";
    }

    @PostMapping
    public String addTask(Authentication authentication, @Valid @ModelAttribute("task") TaskDto task, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("projects", projectsService.getAllProjects());
            model.addAttribute("users", usersService.getActiveUsers());
            return "add-task";
        }

        task.setAuthor(usersService.getUserByEmail(authentication.getName()));

        tasksService.addTask(task);

        return "redirect:/tasks";
    }

}
