package com.example.todo.task;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateTaskDTO {
    @NotBlank
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

    public CreateTaskDTO() {

    }
    
    public CreateTaskDTO(String taskname, LocalDate dueDate, boolean isCompleted, boolean isArchived) {
        this.taskname = taskname;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
        this.isArchived = isArchived;
    }
}
