package com.example.todo.category;



import jakarta.validation.constraints.NotBlank;

public class CreateCategoryDTO {
    
    @NotBlank
    private String catname;

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public CreateCategoryDTO() {
        
    }

    public CreateCategoryDTO(String catname) {
        this.catname = catname;
    }
}
