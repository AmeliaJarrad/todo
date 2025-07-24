package com.example.todo.task;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Pattern;

public class UpdateTaskDTO {

    @Pattern(regexp = ".*\\S.*", message = "Taskname cannot be empty")
    private String taskname;

    @FutureOrPresent
    private LocalDate dueDate;
    
    @JsonProperty("isCompleted")
    private Boolean isCompleted;
    
    @JsonProperty("isArchived")
    private Boolean isArchived;

    //new fields for category handling

    private List<Long> categoryIds;           
    // for selecting existing categories
    private List<String> newCategoryNames;    
    
    // for creating new categories

    // private List<String> categoryNames;

    // public List<String> getCategoryNames() {
    //     return categoryNames;
    // }

    // public void setCategoryNames(List<String> categoryNames) {
    //     this.categoryNames = categoryNames;
    // }

    public String getTaskname() {
        return taskname;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setTaskname(String taskname) {
    this.taskname = taskname;   
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

    //new getters and setters for the category handling changes

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

//fixed my lower case booleans (primitives) to upper case (object types)
    
}
