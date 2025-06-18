package com.example.todo.category;



import jakarta.validation.constraints.NotBlank;

public class CreateCatagoryDTO {
    
    @NotBlank
    private String catname;

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public CreateCatagoryDTO() {
        
    }

    public CreateCatagoryDTO(String catname) {
        this.catname = catname;
    }
}
