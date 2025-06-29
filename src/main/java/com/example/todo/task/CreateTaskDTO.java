package com.example.todo.task;

import java.time.LocalDate;
import java.util.List;

import org.springframework.lang.Nullable;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class CreateTaskDTO {
    @NotBlank
    private String taskname;


    @FutureOrPresent
    @Nullable
    private LocalDate dueDate;

    @NotNull
    private Boolean isCompleted;

    @NotNull
    private Boolean isArchived;

    //making a change here so I can select from existing cats or add a new if needed

    // @NotNull
    // private List<String> categoryNames;

    // @NotNull
    // @Size(min = 1, message = "At least one category must be provided")
    // public List<String> getCategoryNames() {
    //     return categoryNames;
    // }

    // public void setCategoryNames(List<String> categoryNames) {
    //     this.categoryNames = categoryNames;
    // }

    //handling validation in the taskservice for these fields

    private List<Long> categoryIds;

    //for adding new categories by name
    private List<String> newCategoryNames;


    public String getTaskname() {
        return taskname;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Boolean getIsCompleted() {
    return isCompleted;
}

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }


    //getters and setters for the change

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public List<String> getNewCategoryNames() {
        return newCategoryNames;
    }

    public void setNewCategoryNames(List<String> newCategoryNames) {
        this.newCategoryNames = newCategoryNames;
    }

    public CreateTaskDTO() {

    }
    
    //changes to the createTaskDTO to account for new fields
    public CreateTaskDTO(String taskname, LocalDate dueDate, Boolean isCompleted, 
    Boolean isArchived, List<Long> categoryIds, List<String> newCategoryNames) {
    this.taskname = taskname;
    this.dueDate = dueDate;
    this.isArchived = isArchived;
    this.isCompleted = isCompleted;
    this.categoryIds = categoryIds;
    this.newCategoryNames = newCategoryNames;
    }
}
