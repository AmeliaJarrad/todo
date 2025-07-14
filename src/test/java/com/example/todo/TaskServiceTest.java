package com.example.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.todo.category.Category;
import com.example.todo.category.CategoryRepository;
import com.example.todo.task.CreateTaskDTO;
import com.example.todo.task.Task;
import com.example.todo.task.TaskService;


import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import jakarta.transaction.Transactional;

@SpringBootTest(classes = TodoApplication.class)
@ActiveProfiles("test")
@Transactional
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private CategoryRepository categoryRepository;

    
    private Category workCategory;
    private Category personalCategory;
    private Category learningCategory;

    @BeforeEach
    public void setup() {
    
        // Seed categories - was getting failing tests because of dupes
        workCategory = new Category();
        workCategory.setCatname("Work");
        categoryRepository.save(workCategory);

        personalCategory = new Category();
        personalCategory.setCatname("Personal");
        categoryRepository.save(personalCategory);

        learningCategory = new Category();
        learningCategory.setCatname("Learning");
        categoryRepository.save(learningCategory);
    }

    @Test
    public void createTask_WithExistingCategory_SavesCorrectly() {
        CreateTaskDTO taskDTO = new CreateTaskDTO();
        taskDTO.setTaskname("Finish report");
        taskDTO.setCategoryIds(List.of(workCategory.getId()));

        Task created = taskService.create(taskDTO);

        assertNotNull(created.getId());
        assertEquals("Finish report", created.getTaskname());
        assertFalse(created.isArchived());
        assertEquals(1, created.getCategories().size());
        assertEquals("Work", created.getCategories().get(0).getCatname());
    }
}

