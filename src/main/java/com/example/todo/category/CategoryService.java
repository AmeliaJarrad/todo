package com.example.todo.category;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private CategoryRespository categoryRespository;
    private ModelMapper modelMapper;

    CategoryService(CategoryRespository categoryRespository, ModelMapper modelMapper) {
        this.categoryRespository = categoryRespository;
        this.modelMapper = modelMapper;
    }

    public Category create(CreateCatagoryDTO data) {
        Category newCategory  = modelMapper.map(data, Category.class);
        Category savedCategory = this.categoryRespository.save(newCategory);
        return savedCategory;
    }

    public List<Category> findAll() {
        return this.categoryRespository.findAll();
    }

    public Optional<Category> findById(Long id) {
        return this.categoryRespository.findById(id);
    }

//So the delete method that Martyna wrote is this:
//Given that I'm ok for the category to delete this might be ok, but then again this method 
//might not be appropriate for here, this is probably more of what would happen in the task, but 
//we don't want hard delete there, it just needs to change it

    public boolean deletebyId(Long id) {
        Optional<Category> foundCat = this.findById(id);
        if(foundCat.isEmpty()) {
            return false;
        }
        Category catFromDb = foundCat.get();
        this.categoryRespository.delete(catFromDb);
        return true;
    }

//************************************************************* */

    public Optional<Category> updateById(Long id, UpdateCategoryDTO data) {
        Optional<Category> foundCat = this.findById(id);

        if(foundCat.isEmpty()) {
            return foundCat;
        }

        Category catFromDb = foundCat.get();

        this.modelMapper.map(data, catFromDb);
        this.categoryRespository.save(catFromDb);
        return Optional.of(catFromDb);
    }
}
