package org.example.todo.model;

import java.time.Instant;

public record UndoEntry(String id, UndoActionType actionType, ToDo toDo, Instant timestamp) {
}
