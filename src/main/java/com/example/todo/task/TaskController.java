package com.example.todo.task;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.common.exceptions.NotFoundException;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;

    TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> create(@Valid @RequestBody CreateTaskDTO data) {
        Task saved = this.taskService.create(data);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    
    //updating get mappings with the ResponseDTO
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAll() {
        List<Task> allTasks = this.taskService.findAll();
        List<TaskResponseDTO> response = new ArrayList<>();

        for(Task task : allTasks) {
            response.add(taskService.mapToDTO(task));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getById(@PathVariable Long id) throws NotFoundException {
        Optional<Task> foundTask = this.taskService.findById(id);
        if (foundTask.isPresent()) {
            TaskResponseDTO dto = taskService.mapToDTO(foundTask.get());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        throw new NotFoundException("Task with id" + id + " does not exist");
    }

//new mapping for get with params, gets either all tasks or filtered tasks
   @GetMapping(params = "category")
    public ResponseEntity<List<Task>> getTasksByCategory(
    @RequestParam("category") List<String> categoryNames) {

    if (categoryNames != null && !categoryNames.isEmpty()) {
        List<Task> filteredTasks = taskService.findByCategoryNames(categoryNames);
        return ResponseEntity.ok(filteredTasks);
    }
     List<Task> allTasks = taskService.findAll();
    return ResponseEntity.ok(allTasks);

    }


    //at the moment I'm holding of on doing the deletemapping, because i need to figure out how to do that, I think
    //that it's going to be changing the isArchived field to true and then returning a filtered list


    @PatchMapping("{id}")
    public ResponseEntity<Task> updateById(@PathVariable Long id, @Valid @RequestBody UpdateTaskDTO data)
    throws NotFoundException {
        Optional<Task> result = this.taskService.updateById(id, data);
        Task updated = result.orElseThrow(
            () -> new NotFoundException("Could not update task with id " + id + " , it does not exist"));
            return new ResponseEntity<>(updated, HttpStatus.OK);
    }



}
