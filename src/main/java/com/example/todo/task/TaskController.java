package com.example.todo.task;

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
    
    @GetMapping
    public ResponseEntity<List<Task>> getAll() {
        List<Task> allTasks = this.taskService.findAll();
        return new ResponseEntity<>(allTasks, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable Long id) throws NotFoundException {
        Optional<Task> foundTask = this.taskService.findById(id);
        if (foundTask.isPresent()) {
            return new ResponseEntity<>(foundTask.get(), HttpStatus.OK);
        }
        throw new NotFoundException("Task with id" + id + " does not exist");
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
