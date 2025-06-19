package com.example.todo.task;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Pattern;

public class UpdateTaskDTO {

    @Pattern(regexp = ".*\\S.*", message = "Taskname cannot be empty")
    private String taskname;

    @FutureOrPresent
    private LocalDate dueDate;
    
    private Boolean isCompleted;
    
    private Boolean isArchived;

    private List<String> categoryNames;

    public List<String> getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(List<String> categoryNames) {
        this.categoryNames = categoryNames;
    }

    public String getTaskname() {
        return taskname;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public Boolean getIsArchived() {
        return isArchived;
    }

//fixed my lower case booleans (primitives) to upper case (object types)
    
}
