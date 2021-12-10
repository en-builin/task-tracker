package ru.pcs.tasktracker.projects;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectsRepository extends JpaRepository<Project, Long> {

    List<Project> findByArchivedIsNullOrderByTitleAsc();
}