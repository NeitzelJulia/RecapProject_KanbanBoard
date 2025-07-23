package org.example.todo.model;

import java.time.Instant;

public record ChangeLogEntry(String id, ChangeActionType actionType, ToDo toDo, Instant timestamp) {
}
