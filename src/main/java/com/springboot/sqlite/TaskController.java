package com.springboot.sqlite;

import main.model.Task;
import main.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/tasks")
    public List<Task> ListTask() {
        Iterable<Task> taskIterable = taskRepository.findAll();

        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : taskIterable) {
            tasks.add(task);
        }
        return tasks;
    }

    @PostMapping("/tasks")
    public int addTask(Task task) {
        if (taskRepository.count() == 0) {
            task.setId(1);
        }
        Task newTask = taskRepository.save(putDeadline(task));

        return newTask.getId();
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<?> getTask(@PathVariable int id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity<>(optionalTask.get(), HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> dellTask(@PathVariable int id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        taskRepository.deleteById(id);
        return new ResponseEntity<>(taskRepository.count(), HttpStatus.OK);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<?> putTaskId(Task newTask, @PathVariable int id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Task modifiedTask = putDeadline(newTask);
        taskRepository.save(modifiedTask);
        return new ResponseEntity<>(modifiedTask, HttpStatus.OK);
    }

    @DeleteMapping("/tasks")
    public ResponseEntity dellAllTasks() {
        if (taskRepository.count() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The list is already empty.");
        }
        taskRepository.deleteAll();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    public static Task putDeadline(Task task) {
        task.setDeadline(task.getYearTask(), task.getMonthTask(), task.getDayTask());
        return task;
    }

}
