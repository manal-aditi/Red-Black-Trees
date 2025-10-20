package com.example.rbt.model;

import java.time.LocalDateTime;

public class Task implements Comparable<Task> {
    private String name;
    private LocalDateTime dueDate;
    private int priority; // 1 = high, 5 = low
    private String description;

    public Task(String name, LocalDateTime dueDate, int priority, String description) {
        this.name = name;
        this.dueDate = dueDate;
        this.priority = priority;
        this.description = description;
    }

    public String getName() { return name; }
    public LocalDateTime getDueDate() { return dueDate; }
    public int getPriority() { return priority; }
    public String getDescription() { return description; }

    @Override
    public int compareTo(Task other) {
        // Sort by due date first, then priority, then name
        int cmp = this.dueDate.compareTo(other.dueDate);
        if (cmp == 0) {
            cmp = Integer.compare(this.priority, other.priority);
            if (cmp == 0) {
                cmp = this.name.compareTo(other.name);
            }
        }
        return cmp;
    }

    @Override
    public String toString() {
        return name + " (Due: " + dueDate + ", Priority: " + priority + ")";
    }
}
