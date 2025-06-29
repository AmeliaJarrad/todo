package com.example.todo.task;

import java.time.LocalDate;
import java.util.List;

public class TaskResponseDTO {
    private Long id;
    private String taskname;
    private LocalDate dueDate;
    private boolean isCompleted;
    private boolean archived;
    private List<String> categories;


    public Long getId() {
        return id;
    }
    
     public void setId(Long id) {
        this.id = id;
    }

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
        return archived;
    }
    public void setArchived(boolean archived) {
        this.archived = archived;
    }
    public List<String> getCategories() {
        return categories;
    }
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

}

//I wanted to be able to show the categories with the tasks, seems best way to do that is with the responseDTO

//Flipping beans