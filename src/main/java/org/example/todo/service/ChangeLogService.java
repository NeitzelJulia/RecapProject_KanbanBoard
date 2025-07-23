package org.example.todo.service;

import org.example.todo.exception.NoChangeLogEntryException;
import org.example.todo.model.ToDo;
import org.example.todo.model.ChangeLogEntry;
import org.example.todo.repository.ToDoRepo;
import org.example.todo.repository.ChangeLogRepo;
import org.springframework.stereotype.Service;

@Service
public class ChangeLogService {

    private final ChangeLogRepo changeLogRepo;
    private final ToDoRepo toDoRepo;

    public ChangeLogService(ChangeLogRepo changeLogRepo, ToDoRepo toDoRepo) {
        this.changeLogRepo = changeLogRepo;
        this.toDoRepo = toDoRepo;
    }

    public ToDo undoLast() {
        ChangeLogEntry entry = changeLogRepo.findTopByOrderByTimestampDesc()
                .orElseThrow(NoChangeLogEntryException::new);

        changeLogRepo.delete(entry);

        ToDo payload = entry.toDo();
        return switch (entry.actionType()) {
            case CREATE -> {
                toDoRepo.deleteById(payload.id());
                yield null;
            }
            case UPDATE, DELETE -> toDoRepo.save(payload);
        };
    }
}
