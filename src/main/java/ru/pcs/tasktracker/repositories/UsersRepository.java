package ru.pcs.tasktracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pcs.tasktracker.model.User;

import java.util.List;

public interface UsersRepository extends JpaRepository<User, String> {
    List<User> findByOrderByNameAsc();

}