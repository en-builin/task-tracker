package ru.pcs.tasktracker.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pcs.tasktracker.services.TasksService;
import ru.pcs.tasktracker.services.UsersService;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 29.11.2021 in project task-tracker
 */
@RequiredArgsConstructor()
@Controller
@RequestMapping("/tasks")
public class TasksController {

    private final TasksService tasksService;
    private final UsersService usersService;

    @GetMapping
    public String getTasksPage(Authentication authentication, Model model) {
        model.addAttribute("currentUser", usersService.getUserNameByEmail(authentication.getName()));
        model.addAttribute("tasks", tasksService.getAllTasks());
        return "tasks";
    }

//    @PostMapping
//    public String addTask(Authentication authentication, @Valid @ModelAttribute("task") TaskDto task, BindingResult result, Model model) {
//
//        if (result.hasErrors()) {
//            return "add-task";
//        }
//
//        task.setAuthorEmail(authentication.getName());
//
//        tasksService.addTask(task);
//
//        return "redirect:/";
//    }

}
