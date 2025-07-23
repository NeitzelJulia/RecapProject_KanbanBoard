package org.example.todo.model;

import lombok.With;

@With
public record ToDo(String id, String description, ToDoStatus status) {
}
