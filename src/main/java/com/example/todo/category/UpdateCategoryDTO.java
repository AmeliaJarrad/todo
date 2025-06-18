package com.example.todo.category;

import jakarta.validation.constraints.Pattern;

public class UpdateCategoryDTO {
    
     @Pattern(regexp = ".*\\S.*", message = "Category name cannot be empty")
    private String catname;

     public String getCatname() {
         return catname;
     }

    
}