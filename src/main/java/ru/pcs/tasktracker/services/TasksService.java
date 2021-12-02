package ru.pcs.tasktracker.services;

import ru.pcs.tasktracker.dto.TaskDto;

import java.util.List;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 29.11.2021 in project task-tracker
 */
public interface TasksService {

    List<TaskDto> getCurrentTasksByAssignee(String name);

    List<TaskDto> getAllTasks();

    void addTask(TaskDto taskDto);

    TaskDto getTaskById(Long id);

    void save(TaskDto taskDto);

    Boolean isModifyAllowed(TaskDto taskDto, String email);
}
