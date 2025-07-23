package org.example.todo.dto;

import org.example.todo.model.ToDoStatus;

public record CreateToDoDto(String description, ToDoStatus status) {
}
