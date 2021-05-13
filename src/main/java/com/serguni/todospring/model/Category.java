package com.serguni.todospring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category extends AuditModel {
    @Id
    @GeneratedValue(generator = "category_generator")
    @SequenceGenerator(
            name = "category_generator",
            sequenceName = "category_sequence",
            initialValue = 1000,
            allocationSize = 1
    )
    private long id;
    private String name;
    private String color;
    @ManyToMany(mappedBy = "categories", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Task> tasks = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}
