package org.example.todo.model;

import java.time.Instant;

public record ChangeLogEntry(String id, ChangeActionType changeActionType, StackType stackType, ToDo before, ToDo after, Instant timestamp) {
}
