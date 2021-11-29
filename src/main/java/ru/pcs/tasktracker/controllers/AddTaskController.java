package ru.pcs.tasktracker.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pcs.tasktracker.dto.TaskDto;
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
//    private final UsersService usersService;

    @GetMapping
    public String getAddTaskForm(Model model) {
        model.addAttribute("task", new TaskDto());
        return "add-task";
    }

    @PostMapping
    public String addTask(Authentication authentication, @Valid TaskDto task, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("task", task);
            return "add-task";
        }

        task.setAuthorEmail(authentication.getName());

        tasksService.addTask(task);

        // TODO перенаправлять в зависимости от источника нажатия кнопки (из какой страницы нажали)
        return "redirect:/";
    }

}
