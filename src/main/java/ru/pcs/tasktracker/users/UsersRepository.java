package ru.pcs.tasktracker.users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<User, String> {

    List<User> findByOrderByNameAsc();

    List<User> findByStateIsOrderByNameAsc(User.State state);
}