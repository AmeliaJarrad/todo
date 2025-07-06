package com.example.todo.category;

public class CategoryResponseDTO {
    private Long id;
    private String catname;

    public CategoryResponseDTO() {}

    public CategoryResponseDTO(Long id, String catname) {
        this.id = id;
        this.catname = catname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }
}

