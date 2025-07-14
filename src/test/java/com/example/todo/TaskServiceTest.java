package com.example.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.todo.category.Category;
import com.example.todo.category.CategoryRepository;
import com.example.todo.common.ValidationErrors;
import com.example.todo.common.exceptions.NotFoundException;
import com.example.todo.common.exceptions.ServiceValidationException;
import com.example.todo.task.CreateTaskDTO;
import com.example.todo.task.Task;
import com.example.todo.task.TaskService;
import com.example.todo.task.UpdateTaskDTO;

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
    public void createTask_WithExistingCategory_SavesCorrectly() throws NotFoundException, ServiceValidationException {
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

    @Test
    public void createTask_WithInvalidCategoryId_ThrowsNotFoundException() {
        CreateTaskDTO dto = new CreateTaskDTO();
        dto.setTaskname("Test with bad category");
        dto.setCategoryIds(List.of(9999L)); // ID that doesn’t exist

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            taskService.create(dto);
        });

        assertEquals("Category ID 9999 not found", thrown.getMessage());
    }

    @Test
    public void updateTask_WithInvalidCategoryId_ThrowsNotFoundException() throws NotFoundException, ServiceValidationException {
        UpdateTaskDTO dto = new UpdateTaskDTO();
        dto.setCategoryIds(List.of(9999L));

        // First create a task to update
        CreateTaskDTO createDto = new CreateTaskDTO();
        createDto.setTaskname("Task to update");
        createDto.setCategoryIds(List.of(workCategory.getId()));
        Task created = taskService.create(createDto);

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            taskService.updateById(created.getId(), dto);
        });

        assertEquals("Category ID 9999 not found", thrown.getMessage());
    }

   @Test
    public void updateTask_NonExistentId_ThrowsNotFoundException() {
        UpdateTaskDTO dto = new UpdateTaskDTO();
        dto.setTaskname("Updated name");

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            taskService.updateById(9999L, dto);
        });

        assertEquals("Task ID 9999 not found", thrown.getMessage());
    }

    @Test
    public void updateTask_MarkAsComplete_SetsIsCompletedTrue() throws NotFoundException, ServiceValidationException {
        // Create initial task
        CreateTaskDTO createDto = new CreateTaskDTO();
        createDto.setTaskname("Complete this");
        createDto.setCategoryIds(List.of(workCategory.getId()));
        Task created = taskService.create(createDto);

        // Update it to mark as complete
        UpdateTaskDTO updateDto = new UpdateTaskDTO();
        updateDto.setIsCompleted(true);
        Task updated = taskService.updateById(created.getId(), updateDto).get();


        assertNotNull(updated);
        assertEquals(true, updated.isCompleted());
    }

    @Test
    public void archiveTask_SetsArchivedToTrue() throws NotFoundException, ServiceValidationException {
        // Create a task
        CreateTaskDTO createDto = new CreateTaskDTO();
        createDto.setTaskname("To be archived");
        createDto.setCategoryIds(List.of(personalCategory.getId()));
        Task created = taskService.create(createDto);

        // Archive it
        boolean result = taskService.archiveById(created.getId());
        assertEquals(true, result);

        // Fetch directly to confirm it's archived
        Task archived = taskService.findById(created.getId()).orElseThrow();
        assertEquals(true, archived.isArchived());
    }

    @Test
    public void archiveTask_NonExistentId_ReturnsFalse() {
        boolean result = taskService.archiveById(9999L);
        assertFalse(result);
    }


    @Test
    public void createTask_WithNewCategoryName_CreatesAndLinksCategory() throws NotFoundException, ServiceValidationException {
        CreateTaskDTO dto = new CreateTaskDTO();
        dto.setTaskname("Workout");
        dto.setNewCategoryNames(List.of("Fitness"));

        Task created = taskService.create(dto);

        assertNotNull(created.getId());
        assertEquals("Workout", created.getTaskname());
        assertEquals(1, created.getCategories().size());
        assertEquals("Fitness", created.getCategories().get(0).getCatname());
    }


    @Test
    public void createTask_WithNoCategories_ThrowsServiceValidationException() {
        CreateTaskDTO dto = new CreateTaskDTO();
        dto.setTaskname("Lonely task");

        ServiceValidationException thrown = assertThrows(ServiceValidationException.class, () -> {
            taskService.create(dto);
        });

        ValidationErrors errors = thrown.getErrors();
        assertTrue(errors.hasErrors());
        assertTrue(errors.getErrors().containsKey("categories"));
        assertEquals("You must provide at least one category (existing or new).", 
        errors.getErrors().get("categories").get(0));
    }



}

