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
        //Adding validation check here to make sure cat exists
        boolean hasExistingCategories = data.getCategoryIds() != null && !data.getCategoryIds().isEmpty();
        boolean hasNewCategories = data.getNewCategoryNames() != null && !data.getNewCategoryNames().isEmpty();

        if (!hasExistingCategories && !hasNewCategories) {
        throw new IllegalArgumentException("You must provide at least one category (existing or new).");
    }

        Task newTask = modelMapper.map(data, Task.class);
        List<Category> categories = new ArrayList<>();
      

        // //call the catagory list and check if catagory exists already, add if doesnt
       
        // if(data.getCategoryNames() != null) {
        //     for(String catname : data.getCategoryNames()) {
        //         Optional<Category> existingCat = categoryRepository.findByCatname(catname);

        //         if(existingCat.isPresent()) {
        //             categories.add(existingCat.get());
        //         } else {
        //             Category newCategory = new Category();
        //             newCategory.setCatname(catname);
        //             Category savedCategory = categoryRepository.save(newCategory);
        //             categories.add(savedCategory);

        //         }
        //     }
        // }

  //refactor here to handle changes to catagories to eventually a dropdown
    if (hasExistingCategories) {
        for (Long id : data.getCategoryIds()) {
            Optional<Category> foundCategory = categoryRepository.findById(id);
            if (foundCategory.isPresent()) {
                categories.add(foundCategory.get());
            } else {
                System.out.println("Category with ID " + id + " not found.");
            }
        }
    }

        // Handle new categories by name
        if (hasNewCategories) {
        for (String name : data.getNewCategoryNames()) {
            Optional<Category> foundCategory = categoryRepository.findByCatname(name);
            if (foundCategory.isPresent()) {
                categories.add(foundCategory.get());
            } else {
                Category newCategory = new Category();
                newCategory.setCatname(name);
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
    return taskRepository.findByArchivedFalse();
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

        System.out.println("Archiving task: " + taskFromDb.getId()); // <- debug log

        taskRepository.saveAndFlush(taskFromDb); // Ensure the change is persisted immediately
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

            if (data.getIsArchived() != null) {
            taskFromDB.setArchived(data.getIsArchived());
        }

    // Handle categories refactor

    List<Category> updatedCategories = new ArrayList<>();

    //Handle existing categories by ID
    if (data.getCategoryIds() != null) {
        for (Long categoryId : data.getCategoryIds()) {
            Optional<Category> existing = categoryRepository.findById(categoryId);
            if (existing.isPresent()) {
                updatedCategories.add(existing.get());
            } else {
                System.out.println("Category ID " + categoryId + " not found.");
            }
        }
    }

    //Handle new cats by name 
    if (data.getNewCategoryNames() != null) {
        for (String name : data.getNewCategoryNames()) {
            Optional<Category> existing = categoryRepository.findByCatname(name);
            if (existing.isPresent()) {
                updatedCategories.add(existing.get());
            } else {
                Category newCategory = new Category();
                newCategory.setCatname(name);
                Category savedCategory = categoryRepository.save(newCategory);
                updatedCategories.add(savedCategory);
            }
        }
    }

        // if (data.getCategoryNames() != null) {
        //     System.out.println("updating catagories to: " + data.getCategoryNames());
        //     List<Category> updatedCategories = new ArrayList<>();

        //     for (String catname : data.getCategoryNames()) {
        //         Optional<Category> existingCat = categoryRepository.findByCatname(catname);

        //         if (existingCat.isPresent()) {
        //             updatedCategories.add(existingCat.get());
        //         } else {
        //             // Create a new Category instance and set the name
        //             Category newCategory = new Category();
        //             newCategory.setCatname(catname);

        //             Category savedCategory = categoryRepository.save(newCategory);
        //             updatedCategories.add(savedCategory);
                    
        //         }
        //     }

        // Replace current cats
        taskFromDB.getCategories().clear();
        taskFromDB.getCategories().addAll(updatedCategories);
        

// //trying to figure out why cat changes not saving
//     System.out.println("Final categories on task before save:");
//     taskFromDB.getCategories().forEach(cat -> System.out.println(cat.getCatname()));

//             //saving the updated task
//      taskRepository.save(taskFromDB);

//     // Re-fetch from DB to get updated timestamps
//         Task refreshed = taskRepository.findById(id).get();
//         return Optional.of(refreshed);

//     }


    // debug logging
    System.out.println("Updated categories:");
    for (Category cat : updatedCategories) {
        System.out.println(" - " + cat.getCatname());

    }

    // Save updated task
    taskRepository.save(taskFromDB);

    // Refresh from DB to get updated timestamps
    Task refreshed = taskRepository.findById(id).get();
    return Optional.of(refreshed);
    

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

    public List<Task> findArchivedTasks() {
       return taskRepository.findByArchivedTrue();
    }
}
