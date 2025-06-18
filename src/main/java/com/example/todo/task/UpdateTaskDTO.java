package com.example.todo.task;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class UpdateTaskDTO {

    @Pattern(regexp = ".*\\S.*", message = "Taskname cannot be empty")
    private String taskname;

    @FutureOrPresent
    private LocalDate dueDate;

    @NotNull
    private boolean isCompleted;

    @NotNull
    private boolean isArchived;

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


    
}
