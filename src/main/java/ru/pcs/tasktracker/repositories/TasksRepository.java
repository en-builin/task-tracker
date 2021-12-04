package ru.pcs.tasktracker.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.pcs.tasktracker.model.Task;
import ru.pcs.tasktracker.model.User;

import java.util.List;

public interface TasksRepository extends JpaRepository<Task, Long> {

    List<Task> findByAssigneeAndFinishedAtIsNull(User user, Sort sort);


}