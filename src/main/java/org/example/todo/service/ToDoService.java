package org.example.todo.service;

import org.example.todo.dto.CreateToDoDto;
import org.example.todo.dto.UpdateToDoDto;
import org.example.todo.exception.ToDoNotFoundException;
import org.example.todo.model.ToDo;
import org.example.todo.model.ChangeActionType;
import org.example.todo.model.ChangeLogEntry;
import org.example.todo.repository.ToDoRepo;
import org.example.todo.repository.ChangeLogRepo;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ToDoService {

    private final ToDoRepo toDoRepo;
    private final IdService idService;
    private final ChangeLogRepo changeLogRepo;

    public ToDoService(ToDoRepo toDoRepo, IdService idService,  ChangeLogRepo changeLogRepo) {
        this.toDoRepo = toDoRepo;
        this.idService = idService;
        this.changeLogRepo = changeLogRepo;
    }

    public List<ToDo> findAll() {
        return toDoRepo.findAll();
    }

    public ToDo findById(String id) {
        return toDoRepo.findById(id).orElse(null);
    }

    public ToDo addTodo(CreateToDoDto createToDoDto) {
        ToDo toDo = new ToDo(
                idService.randomId(),
                createToDoDto.description(),
                createToDoDto.status()
                );

        ToDo saved = toDoRepo.save(toDo);

        changeLogRepo.save(new ChangeLogEntry(
                idService.randomId(),
                ChangeActionType.CREATE,
                saved,
                Instant.now()
        ));

        return saved;
    }

    public ToDo updateTodo(String id, UpdateToDoDto updateToDoDto) {
        ToDo existingToDo = toDoRepo.findById(id)
                .orElseThrow(() -> new ToDoNotFoundException(id));

        changeLogRepo.save(new ChangeLogEntry(
                idService.randomId(),
                ChangeActionType.UPDATE,
                existingToDo,
                Instant.now()
        ));

        ToDo updated = new ToDo(
                existingToDo.id(),
                updateToDoDto.description(),
                updateToDoDto.status()
        );

        return toDoRepo.save(updated);
    }

    public void deleteTodo(String id) {
        ToDo existingToDo = toDoRepo.findById(id)
                .orElseThrow(() -> new ToDoNotFoundException(id));

        changeLogRepo.save(new ChangeLogEntry(
                idService.randomId(),
                ChangeActionType.DELETE,
                existingToDo,
                Instant.now()
        ));

        toDoRepo.deleteById(existingToDo.id());
    }
}
