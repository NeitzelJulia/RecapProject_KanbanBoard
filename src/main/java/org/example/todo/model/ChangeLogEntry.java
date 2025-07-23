package org.example.todo.model;

import lombok.With;

import java.time.Instant;

@With
public record ChangeLogEntry(
        String id,
        ChangeActionType changeActionType,
        StackType stackType,
        ToDo before,
        ToDo after,
        Instant timestamp
) {}
