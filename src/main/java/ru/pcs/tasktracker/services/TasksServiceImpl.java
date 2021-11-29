package ru.pcs.tasktracker.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.pcs.tasktracker.dto.TaskDto;
import ru.pcs.tasktracker.model.Task;
import ru.pcs.tasktracker.repositories.TasksRepository;

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

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private UsersService usersService;

    @Override
    public List<TaskDto> getTasksByAssignee(String email) {
        return tasksRepository.findByAssignee_EmailOrderByCreatedAsc(email, Sort.by("created"))
                .stream().map(TaskDto::from).collect(Collectors.toList());
    }

    @Override
    public void addTask(TaskDto taskDto) {
        Task task = Task.builder()
                .assignee(usersService.getUserByEmail(taskDto.getAssigneeEmail()))
                .author(usersService.getUserByEmail(taskDto.getAuthorEmail()))
                .created(new Timestamp(System.currentTimeMillis()))
                .fullDescription(taskDto.getFullDescription())
                .shortDescription(taskDto.getShortDescription())
                .build();

        tasksRepository.save(task);
    }
}
