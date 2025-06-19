package com.example.todo.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.todo.category.Category;
import com.example.todo.category.CategoryRepository;

@Service
public class TaskService {
    
    private TaskRepository taskRepository;
    private ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    public TaskService(TaskRepository taskRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    public Task create(CreateTaskDTO data) {
        //turning the CreateTaskDTO object to a Task object

        Task newTask = modelMapper.map(data, Task.class);

        //call the catagory list and check if catagory exists already, add if doesnt
        List<Category> categories = new ArrayList<>();
        if(data.getCategoryNames() != null) {
            for(String catname : data.getCategoryNames()) {
                Optional<Category> existingCat = categoryRepository.findByCatname(catname);

                if(existingCat.isPresent()) {
                    categories.add(existingCat.get());
                } else {
                    Category newCategory = new Category();
                    newCategory.setCatname(catname);
                    Category savedCategory = categoryRepository.save(newCategory);
                    categories.add(savedCategory);

                }
            }
        }

        //now gotta attach the category to the task
        for(Category category : categories) {
            newTask.addCategory(category);
        }

        //now save and return
        Task savedTask = this.taskRepository.save(newTask);
        return savedTask;
    }

    public List<Task> findAll() {
        return this.taskRepository.findAll();
    }

    public Optional<Task> findById(Long id) {
        return this.taskRepository.findById(id);
    }

    //delete mapping not fully here yet, need to figure out soft delete which will be changing the tag to archived

    public boolean deletebyId(Long id) {
        //check if task exists
        Optional<Task> foundTask = this.findById(id);
        if (foundTask.isEmpty()) {
            return false;
        }
        Task taskFromDb = foundTask.get();
        // this.taskRepository.delete(taskFromDb);
        //line 45 here is what needs to change, we just want to change the isArchived to true and return that
        return true;
    }

    public Optional<Task> updateById(Long id, UpdateTaskDTO data) {
        Optional<Task> foundTask = this.findById(id);

        if(foundTask.isEmpty()) {
            return foundTask;
        }

        Task taskFromDB = foundTask.get();
        this.modelMapper.map(data, taskFromDB);
        this.taskRepository.save(taskFromDB);
        return Optional.of(taskFromDB);
    }
}
