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

    //now that I got the archived mapping, adding that to the findall so
    //archived tasks don't appear
    public List<Task> findAll() {
        return this.taskRepository.findAll().stream()
            .filter(task -> !task.isArchived())
            .toList();
    }

    public Optional<Task> findById(Long id) {
        return this.taskRepository.findById(id);
    }

    //delete mapping with soft delete 

    public boolean archiveById(Long id) {
        //check if task exists
        Optional<Task> foundTask = this.findById(id);
        if (foundTask.isEmpty()) {
            return false;
        }
        Task taskFromDb = foundTask.get();
        taskFromDb.setArchived(true); //here's soft deleting
        taskRepository.save(taskFromDb);

        return true;
    }

    //adding the category handling 
    public Optional<Task> updateById(Long id, UpdateTaskDTO data) {
    Optional<Task> foundTask = this.findById(id);

        if (foundTask.isEmpty()) {
            return Optional.empty();
        }

        Task taskFromDB = foundTask.get();

        //  modelMapper to skip nulls
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(data, taskFromDB);

    // Handle categories 
        if (data.getCategoryNames() != null) {
            List<Category> updatedCategories = new ArrayList<>();

            for (String catname : data.getCategoryNames()) {
                Optional<Category> existingCat = categoryRepository.findByCatname(catname);

                if (existingCat.isPresent()) {
                    updatedCategories.add(existingCat.get());
                } else {
                    // Create a new Category instance and set the name
                    Category newCategory = new Category();
                    newCategory.setCatname(catname);

                    Category savedCategory = categoryRepository.save(newCategory);
                    updatedCategories.add(savedCategory);
                }
            }

        // Replace the task's categories with the updated list
        taskFromDB.getCategories().clear();
        taskFromDB.getCategories().addAll(updatedCategories);
        }

        //saving the updated task
     taskRepository.save(taskFromDB);

    return Optional.of(taskFromDB);

    }



    //this one is for finding by cat name - now with case insensitivity
    public List<Task> findByCategoryNames(List<String> categoryNames) {
    List<String> lowered = categoryNames.stream()
        .map(String::toLowerCase)
        .toList();

    return taskRepository.findByCategoryNamesIgnoreCase(lowered);
    }


    // method for the TaskResponseDTO

    public TaskResponseDTO mapToDTO(Task task) {
    TaskResponseDTO dto = new TaskResponseDTO();

    dto.setId(task.getId());
    dto.setTaskname(task.getTaskname());
    dto.setDueDate(task.getDueDate());
    dto.setCompleted(task.isCompleted());
    dto.setArchived(task.isArchived());

    // Convert category objects to list of catname strings
    List<String> categoryNames = new ArrayList<>();
    for (Category cat : task.getCategories()) {
        categoryNames.add(cat.getCatname());
    }
    dto.setCategories(categoryNames);

    return dto;
}
}
