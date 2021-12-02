package ru.pcs.tasktracker.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pcs.tasktracker.dto.ProjectDto;
import ru.pcs.tasktracker.model.Project;
import ru.pcs.tasktracker.repositories.ProjectsRepository;

import java.util.List;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 01.12.2021 in project task-tracker
 */
@Service
@RequiredArgsConstructor
public class ProjectsServiceImpl implements ProjectsService {

    private final ProjectsRepository projectsRepository;

    @Override
    public List<ProjectDto> getAllProjects() {
        return ProjectDto.from(projectsRepository.findByArchivedIsNullOrderByTitleAsc());
    }

    @Override
    public void addProject(ProjectDto projectDto) {
        Project project = Project.builder()
                .title(projectDto.getTitle())
                .rate(projectDto.getRate())
                .build();

        projectsRepository.save(project);
    }
}
