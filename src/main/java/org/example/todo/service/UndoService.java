package org.example.todo.service;

import org.example.todo.exception.NoUndoOperationException;
import org.example.todo.model.ToDo;
import org.example.todo.model.UndoEntry;
import org.example.todo.repository.ToDoRepo;
import org.example.todo.repository.UndoRepo;
import org.springframework.stereotype.Service;

@Service
public class UndoService {

    private final UndoRepo undoRepo;
    private final ToDoRepo toDoRepo;

    public UndoService(UndoRepo undoRepo, ToDoRepo toDoRepo) {
        this.undoRepo = undoRepo;
        this.toDoRepo = toDoRepo;
    }

    public ToDo undoLast() {
        UndoEntry entry = undoRepo.findTopByOrderByTimestampDesc()
                .orElseThrow(NoUndoOperationException::new);

        undoRepo.delete(entry);

        ToDo payload = entry.toDo();
        return switch (entry.actionType()) {
            case CREATE -> {
                toDoRepo.deleteById(payload.id());
                yield null;
            }
            case UPDATE ->  {
                // ToDo
                yield null;
            }
            case DELETE -> {
                //ToDo
                yield null;
            }
        };
    }
}
