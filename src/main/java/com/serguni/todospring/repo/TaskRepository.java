package com.serguni.todospring.repo;

import com.serguni.todospring.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findByUserIdAndId(long userId, long id);
    boolean existsByUserIdAndId(long userId, long id);
}
