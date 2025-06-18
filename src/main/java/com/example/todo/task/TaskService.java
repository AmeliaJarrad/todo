package com.example.todo.task;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    
    private TaskRepository taskRepository;
    private ModelMapper modelMapper;

    TaskService(TaskRepository taskRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    public Task create(CreateTaskDTO data) {
        //turning the CreateTaskDTO object to a Task object

        Task newTask = modelMapper.map(data, Task.class);
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
