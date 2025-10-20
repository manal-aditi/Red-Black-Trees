package com.example.rbt.controller;

import com.example.rbt.model.Task;
import com.example.rbt.service.TaskScheduler;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:3000")
public class TaskController {

    private final TaskScheduler scheduler;

    public TaskController(TaskScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostMapping("/add")
    public Map<String, Object> addTask(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        LocalDateTime dueDate = LocalDateTime.parse(body.get("dueDate"));
        int priority = Integer.parseInt(body.get("priority"));
        String description = body.get("description");

        scheduler.addTask(new Task(name, dueDate, priority, description));
        return Map.of("ok", true);
    }

    @GetMapping("/all")
    public List<Task> getAllTasks() {
        return scheduler.getAllTasks();
    }
}
