package com.example.todo.task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.todo.category.Category;
import com.example.todo.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {

    //relationships with tasks_categories
    @ManyToMany
    @JoinTable(
        name = "tasks_categories",
        joinColumns = @JoinColumn(name = "tasks_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();

    
    @Column (nullable = false)
    private String taskname;


    @Column (nullable = true)
    private LocalDate dueDate;

    @Column (nullable = false)
    private boolean isCompleted;

    @Column (nullable = false)
    private boolean isArchived;

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }


    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean isArchived) {
        this.isArchived = isArchived;
    }

    //Adding these as an easy way to add/remove cats to tasks

    public void addCategory(Category category) {
    this.categories.add(category);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
    }

    
}
