package com.example.todo.category;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.common.exceptions.NotFoundException;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;


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
    public ResponseEntity<Category> create(@Valid @RequestBody CreateCategoryDTO data) {
    System.out.println("POST /categories with catname" + data.getCatname());
    Category saved = this.categoryService.create(data);
    return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Category>> getAll() {
        List<Category> allCats = this.categoryService.findAll();
        return new ResponseEntity<>(allCats, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id) throws NotFoundException {
        Optional<Category> foundCat = this.categoryService.findById(id);
        if (foundCat.isPresent()) {
            return new ResponseEntity<>(foundCat.get(), HttpStatus.OK);
        }
        throw new NotFoundException("Category with id" + id + " does not exist");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) throws NotFoundException {
        boolean deleted = this.categoryService.deleteById(id);
        if (deleted) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        throw new NotFoundException("Category with id " + id + " does not exist");
    }

    @PatchMapping("{id}")
    public ResponseEntity<Category> updateById(@PathVariable Long id, @Valid @RequestBody UpdateCategoryDTO data)
            throws NotFoundException {
        Optional<Category> result = this.categoryService.updateById(id, data);
        Category updated = result.orElseThrow(
                () -> new NotFoundException("Could not update category with id " + id + " , it does not exists"));

        return new ResponseEntity<>(updated, HttpStatus.OK);

    }

    
}

