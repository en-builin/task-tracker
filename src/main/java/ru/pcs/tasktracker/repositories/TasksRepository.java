package ru.pcs.tasktracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pcs.tasktracker.model.Task;

public interface TasksRepository extends JpaRepository<Task, Long> {
}