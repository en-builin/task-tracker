package ru.pcs.tasktracker.tasks;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pcs.tasktracker.projects.ProjectsService;
import ru.pcs.tasktracker.users.UsersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;

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
    private final ProjectsService projectsService;

    @GetMapping
    public String getTasksPage(Authentication authentication, Model model) {
        model.addAttribute("currentUser", usersService.getUserByEmail(authentication.getName()));
        model.addAttribute("tasks", tasksService.getAllTasks());
        return "tasks";
    }

    @GetMapping("/{id}")
    public String getTaskEditPage(Authentication authentication, @PathVariable Long id,
                                  @RequestParam Map<String, String> params, Model model) {
        model.addAttribute("taskDto", tasksService.getTaskById(id));
        model.addAttribute("projects", projectsService.getAllProjects());
        model.addAttribute("users", usersService.getActiveUsers());
        if (params.getOrDefault("from", "index").equals("tasks")) {
            model.addAttribute("returnUrl", "/tasks");
        } else {
            model.addAttribute("returnUrl", "/");
        }
        return "task";
    }

    @PostMapping("/{id}")
    public String saveTask(@PathVariable Long id, Authentication authentication, @Valid TaskDto taskDto,
                           BindingResult result, Model model,
                           HttpServletRequest request, HttpServletResponse response) {

        if (!Objects.equals(id, taskDto.getId())) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

        if (!tasksService.isModifyAllowed(taskDto, authentication.getName())
                && authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ADMIN"))) {
            // ???????? ???? ?????????? ?? ?????????????????? ???????????? (= ???? ?????????? ?? ???? ?????????????????????? ????????????) ???????????????????? 403 ???????????? ???? POST-????????????
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }

        if (result.hasErrors()) {
            model.addAttribute("taskDto", taskDto);
            model.addAttribute("users", usersService.getActiveUsers());
            model.addAttribute("projects", projectsService.getAllProjects());
            return "task";
        }

        tasksService.save(taskDto);

        return "redirect:/";
    }

}
