package ru.pcs.tasktracker.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.pcs.tasktracker.model.Task;

import java.util.List;

public interface TasksRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignee_Email(String email, Sort sort);
}