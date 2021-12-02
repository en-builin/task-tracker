package ru.pcs.tasktracker.services;

import ru.pcs.tasktracker.dto.ProjectDto;

import java.util.List;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 01.12.2021 in project task-tracker
 */
public interface ProjectsService {

    List<ProjectDto> getAllProjects();

    void addProject(ProjectDto projectDto);
}
