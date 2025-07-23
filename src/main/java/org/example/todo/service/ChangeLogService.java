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
        return processLast(StackType.UNDO);
    }

    public ToDo redoLast() {
        return processLast(StackType.REDO);
    }

    private ToDo processLast(StackType readFrom) {
        StackType writeTo = (readFrom == StackType.UNDO)
                ? StackType.REDO
                : StackType.UNDO;

        ChangeLogEntry entry = changeLogRepo
                .findTopByStackTypeOrderByTimestampDesc(readFrom)
                .orElseThrow(NoChangeLogEntryException::new);

        ChangeLogEntry moved = entry
                .withStackType(writeTo)
                .withTimestamp(Instant.now());
        changeLogRepo.save(moved);

        return switch (entry.changeActionType()) {
            case CREATE:
                if (readFrom == StackType.UNDO) {
                    toDoRepo.deleteById(entry.after().id());
                    yield null;
                } else {
                    yield toDoRepo.save(entry.after());
                }
            case UPDATE:
                if (readFrom == StackType.UNDO) {
                    yield toDoRepo.save(entry.before());
                } else  {
                    yield toDoRepo.save(entry.after());
                }
            case DELETE:
                if (readFrom == StackType.UNDO) {
                    yield toDoRepo.save(entry.before());
                } else {
                    toDoRepo.deleteById(entry.before().id());
                    yield null;
                }
        };
    }
}
