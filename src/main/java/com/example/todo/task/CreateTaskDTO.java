package com.example.todo.task;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateTaskDTO {
    @NotBlank
    private String taskname;


    @FutureOrPresent
    private LocalDate dueDate;

    @NotNull
    private boolean isCompleted;

    @NotNull
    private boolean isArchived;

    @NotNull
    private List<String> categoryNames;

    @NotNull
    @Size(min = 2, message = "At least one category must be provided")
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

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public CreateTaskDTO() {

    }
    
    public CreateTaskDTO(String taskname, LocalDate dueDate, boolean isCompleted, boolean isArchived, @NotNull List<String> categoryNames) {
        this.taskname = taskname;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
        this.isArchived = isArchived;
        this.categoryNames = categoryNames;
    }
}
