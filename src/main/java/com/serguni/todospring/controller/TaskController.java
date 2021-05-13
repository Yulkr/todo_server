package com.serguni.todospring.controller;

import com.serguni.todospring.model.Task;
import com.serguni.todospring.model.User;
import com.serguni.todospring.repo.TaskRepository;
import com.serguni.todospring.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users/{id}/tasks")
    public void create(@PathVariable long id, @RequestBody Task task) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            task.setUser(user);
            taskRepository.save(task);
        } else {
            ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/users/{userId}/tasks/{taskId}")
    public ResponseEntity<?> read(@PathVariable long userId, @PathVariable long taskId) {
        Task task = taskRepository.findByUserIdAndId(userId, taskId);
        if (task != null) {
            return new ResponseEntity<>(task, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/users/{userId}/tasks/{taskId}")
    public ResponseEntity<?> update(@PathVariable long userId,
                                    @PathVariable long taskId,
                                    @RequestBody Task upTask) {
        Task task = taskRepository.findByUserIdAndId(userId, taskId);
        if (task != null) {
            upTask.setId(taskId);
            taskRepository.save(upTask);
            return new ResponseEntity<>(upTask, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/users/{userId}/tasks/{taskId}")
    public void delete(@PathVariable long userId,
                       @PathVariable long taskId) {
        Task task = taskRepository.findByUserIdAndId(userId, taskId);
        if (task != null) {
            taskRepository.delete(task);
            ResponseEntity.ok().build();
        } else {
            ResponseEntity.notFound().build();
        }
    }
}
