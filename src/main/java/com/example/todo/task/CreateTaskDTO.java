package com.example.todo.task;

import java.time.LocalDate;
import java.util.List;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class CreateTaskDTO {
    @NotBlank
    private String taskname;


    @FutureOrPresent
    @Nullable
    private LocalDate dueDate;

    //Adding these JsonProperty annotations, issues with the archived/isArchived being read, its
    //some naming convention issue refer to Attributes.md more reading in htere on it

    @NotNull
    @JsonProperty("isCompleted")
    private Boolean isCompleted;

    @NotNull
    @JsonProperty("isArchived")
    private Boolean archived;

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

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getIsCompleted() {
    return isCompleted;
}

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
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
    Boolean archived, List<Long> categoryIds, List<String> newCategoryNames) {
    this.taskname = taskname;
    this.dueDate = dueDate;
    this.archived = archived;
    this.isCompleted = isCompleted;
    this.categoryIds = categoryIds;
    this.newCategoryNames = newCategoryNames;
    }
}

//so much wrestling with the JSON jackson spring bean naming convention headaches!!