package com.example.todo.category;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//Don't think this is correct - I'll need to change these various Category files, I think they're supposed to be more like
// how the loan or member patterns are from the example, while tasks is more like the books

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private CategoryService categoryService;

    CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    @PostMapping
    public ResponseEntity<Category> create(@Valid @RequestBody CreateCatagoryDTO data) {
    Category saved = this.categoryService.create(data);
    return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("path")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
}
