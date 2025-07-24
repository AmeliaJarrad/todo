package com.example.todo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import com.example.todo.category.Category;
import com.example.todo.category.CategoryRepository;
import com.example.todo.task.Task;
import com.example.todo.task.TaskRepository;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TaskControllerEndToEndTest {
     @LocalServerPort
    private int port;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category workCategory;
    private Category personalCategory;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        taskRepository.deleteAll();
        categoryRepository.deleteAll();

        workCategory = new Category();
        workCategory.setCatname("Work");
        categoryRepository.save(workCategory);

        personalCategory = new Category();
        personalCategory.setCatname("Personal");
        categoryRepository.save(personalCategory);
    }


    @Test
    public void getAllTasks_WhenTasksExist_ReturnsTasks() {
        Task task = new Task();
        task.setTaskname("Finish unit tests");
        task.addCategory(workCategory);
        taskRepository.save(task);

        given()
            .when().get("/tasks")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("$", hasSize(1))
            .body("[0].taskname", equalTo("Finish unit tests"))
            .body("[0].categories[0].catname", equalTo("Work"));
    }

    @Test
    public void getTaskById_ExistingId_ReturnsTask() {
        Task task = new Task();
        task.setTaskname("Get by ID test");
        task.addCategory(personalCategory);
        task = taskRepository.save(task);

        given()
            .when().get("/tasks/" + task.getId())
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("taskname", equalTo("Get by ID test"))
            .body("categories[0].catname", equalTo("Personal"));
        }

    @Test
    public void getTaskById_NonExistentId_ReturnsNotFound() {
        given()
            .when().get("/tasks/99999")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

   @Test
    public void createTask_ValidDataWithCategory_ReturnsCreated() {
        // Prepare request body as a Map or a DTO instance
        Map<String, Object> newTask = new HashMap<>();
        newTask.put("taskname", "Finish unit tests");
        newTask.put("isCompleted", false);
        newTask.put("isArchived", false);
        newTask.put("dueDate", "2025-07-14"); // ISO LocalDate string format

        // Add the existing category ID
        newTask.put("categoryIds", List.of(workCategory.getId()));

        // Optionally add new category names (empty for this test)
        newTask.put("newCategoryNames", List.of());

        given()
            .contentType("application/json")
            .body(newTask)
            .when()
            .post("/tasks")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .body("taskname", equalTo("Finish unit tests"))
            .body("completed", equalTo(false))
            .body("archived", equalTo(false))
            .body("categories[0].catname", equalTo(workCategory.getCatname()));
    }


   @Test
    public void createTask_ValidDataNoCategory_ReturnsBadRequest() {
        String taskJson = """
            {
                "taskname": "Read a book",
                "dueDate": "2025-12-31",
                "isCompleted": false,
                "archived": false,
                "categoryIds": [],
                "newCategoryNames": []
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .body(taskJson)
        .when()
            .post("/tasks")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    public void createTask_InvalidCategoryId_ReturnsBadRequest() {
        var newTask = new HashMap<String, Object>();
        newTask.put("taskname", "Invalid Category Test");
        newTask.put("categoryIds", List.of(9999L)); // non-existent ID

        given()
            .contentType("application/json")
            .body(newTask)
            .when().post("/tasks")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    public void updateTask_ValidData_ReturnsUpdatedTask() {
        // Setup: create initial task
        Task task = new Task();
        task.setTaskname("Initial task");
        task.addCategory(workCategory);
        task = taskRepository.save(task);

        // Prepare update payload
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("taskname", "Updated task name");
        updateData.put("isCompleted", true);
        updateData.put("isArchived", false);
        updateData.put("categoryIds", List.of(personalCategory.getId()));  // change category
        updateData.put("newCategoryNames", List.of());  // no new categories

        given()
            .contentType(ContentType.JSON)
            .body(updateData)
            .when()
            .patch("/tasks/" + task.getId())
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("taskname", equalTo("Updated task name"))
            .body("completed", equalTo(true))
            .body("archived", equalTo(false))
            .body("categories[0].catname", equalTo(personalCategory.getCatname()));
    }

    @Test
    public void archiveTask_ExistingTask_ReturnsNoContent() {
        // Setup: create initial task
        Task task = new Task();
        task.setTaskname("Task to archive");
        task.addCategory(workCategory);
        task = taskRepository.save(task);

        given()
            .when()
            .delete("/tasks/" + task.getId())
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());

        // Verify task is archived in DB
        Task archived = taskRepository.findById(task.getId()).orElseThrow();
        assertTrue(archived.isArchived());
    }


}
