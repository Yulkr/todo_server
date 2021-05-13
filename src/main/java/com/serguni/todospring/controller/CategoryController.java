package com.serguni.todospring.controller;

import com.serguni.todospring.model.Category;
import com.serguni.todospring.model.Task;
import com.serguni.todospring.repo.CategoryRepository;
import com.serguni.todospring.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
public class CategoryController {

    private static final String CATEGORY_PATH = "/users/{userId}/tasks/{taskId}/categories/";

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping(CATEGORY_PATH)
    public void create(@PathVariable long userId,
                  @PathVariable long taskId,
                  @RequestBody Category category) {

        Task task = taskRepository.findByUserIdAndId(userId, taskId);

        if (task != null) {
            Set<Task> tasksOfCategory = category.getTasks();
            if (tasksOfCategory == null) {
                tasksOfCategory = new HashSet<>();
            }
            tasksOfCategory.add(task);
            category = categoryRepository.save(category);
            task.getCategories().add(category);
        } else {
            ResponseEntity.notFound().build();
        }
    }

    @GetMapping(CATEGORY_PATH + "{categoryId}")
    public ResponseEntity<Category> read(@PathVariable long userId,
                     @PathVariable long taskId,
                     @PathVariable long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);

        if (taskRepository.existsByUserIdAndId(userId, taskId) && category != null) {
            return new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(CATEGORY_PATH + "{categoryId}")
    public ResponseEntity<Category> update(@PathVariable long userId,
                                           @PathVariable long taskId,
                                           @PathVariable long categoryId,
                                           @RequestBody Category upCategory) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category != null && taskRepository.existsByUserIdAndId(userId, taskId)) {
            upCategory.setId(categoryId);
            return new ResponseEntity<>(categoryRepository.save(upCategory), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(CATEGORY_PATH + "{categoryId}")
    public void delete(@PathVariable long userId,
                       @PathVariable long taskId,
                       @PathVariable long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        System.out.println(taskRepository.existsByUserIdAndId(userId, taskId));
        if (category != null && taskRepository.existsByUserIdAndId(userId, taskId)) {
            categoryRepository.delete(category);
            ResponseEntity.ok().build();
        } else {
            ResponseEntity.notFound().build();
        }
    }
}
