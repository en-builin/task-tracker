package ru.pcs.tasktracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pcs.tasktracker.model.Project;

import java.util.List;

public interface ProjectsRepository extends JpaRepository<Project, Long> {
    List<Project> findByArchivedIsNullOrderByTitleAsc();
}