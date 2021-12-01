package ru.pcs.tasktracker.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.pcs.tasktracker.dto.TaskDto;
import ru.pcs.tasktracker.model.Task;
import ru.pcs.tasktracker.model.User;
import ru.pcs.tasktracker.repositories.TasksRepository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 29.11.2021 in project task-tracker
 */
@Service
@RequiredArgsConstructor
public class TasksServiceImpl implements TasksService {

    private final TasksRepository tasksRepository;

    private final UsersService usersService;

    @Override
    public List<TaskDto> getCurrentTasksByAssignee(String email) {
        return tasksRepository.findByAssignee_EmailAndFinishedIsNull(email, Sort.by(Sort.Direction.DESC, "created"))
                .stream().map(TaskDto::from).collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getAllTasks() {
        return TaskDto.from(tasksRepository.findAll(Sort.by(Sort.Direction.DESC, "created")));
    }

    @Override
    public void addTask(TaskDto taskDto) {
        Task task = Task.builder()
                .assignee(usersService.getUserByEmail(taskDto.getAssigneeEmail()))
                .author(usersService.getUserByEmail(taskDto.getAuthorEmail()))
                .created(new Timestamp(System.currentTimeMillis()))
                .fullDescription(taskDto.getFullDescription())
                .shortDescription(taskDto.getShortDescription())
                .project(taskDto.getProject())
                .hours(BigDecimal.ZERO)
                .build();

        tasksRepository.save(task);
    }

    @Override
    public TaskDto getTaskById(Long id) {
        return TaskDto.from(tasksRepository.getById(id));
    }

    @Override
    public void save(TaskDto taskDto) {

        Task task = tasksRepository.getById(taskDto.getId());

        task.setAssignee(usersService.getUserByEmail(taskDto.getAssigneeEmail()));
        task.setProject(taskDto.getProject());
        task.setShortDescription(taskDto.getShortDescription());
        task.setFullDescription(taskDto.getFullDescription());
        task.setHours(taskDto.getHours());

        if (taskDto.getIsFinished() && task.getFinished() == null) {
            task.setFinished(new Timestamp(System.currentTimeMillis()));
        }

        tasksRepository.save(task);
    }

    @Override
    public Boolean isModifyAllowed(TaskDto taskDto, String email) {
        Task task = tasksRepository.getById(taskDto.getId());
        User userByEmail = usersService.getUserByEmail(email);
        return (task.getAuthor().equals(userByEmail) || task.getAssignee().equals(userByEmail));
    }
}
