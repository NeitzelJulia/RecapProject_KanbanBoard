package org.example.todo.service;

import org.example.todo.exception.NoChangeLogEntryException;
import org.example.todo.model.StackType;
import org.example.todo.model.ToDo;
import org.example.todo.model.ChangeLogEntry;
import org.example.todo.repository.ToDoRepo;
import org.example.todo.repository.ChangeLogRepo;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ChangeLogService {

    private final ChangeLogRepo changeLogRepo;
    private final ToDoRepo toDoRepo;

    public ChangeLogService(ChangeLogRepo changeLogRepo, ToDoRepo toDoRepo) {
        this.changeLogRepo = changeLogRepo;
        this.toDoRepo = toDoRepo;
    }

    public ToDo undoLast() {
        ChangeLogEntry entry = changeLogRepo.findTopByStackTypeOrderByTimestampDesc(StackType.UNDO)
                .orElseThrow(NoChangeLogEntryException::new);

        ChangeLogEntry redoEntry = new ChangeLogEntry(
                entry.id(),
                entry.changeActionType(),
                StackType.REDO,
                entry.before(),
                entry.after(),
                Instant.now()
        );
        changeLogRepo.save(redoEntry);

        return switch (entry.changeActionType()) {
            case CREATE -> {
                toDoRepo.deleteById(entry.after().id());
                yield null;
            }
            case UPDATE, DELETE -> toDoRepo.save(entry.before());
        };
    }
}
