package org.example.todo.dto;

import org.example.todo.model.ToDoStatus;

public record UpdateToDoDto(String description, ToDoStatus status) {
}
