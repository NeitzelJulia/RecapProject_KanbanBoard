package org.example.todo.service;

import org.example.todo.dto.CreateToDoDto;
import org.example.todo.dto.UpdateToDoDto;
import org.example.todo.exception.ToDoNotFoundException;
import org.example.todo.model.ToDo;
import org.example.todo.repository.ToDoRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoService {

    private final ToDoRepo toDoRepo;
    private final IdService idService;

    public ToDoService(ToDoRepo toDoRepo, IdService idService) {
        this.toDoRepo = toDoRepo;
        this.idService = idService;
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

        return toDoRepo.save(toDo);
    }

    public ToDo updateTodo(String id, UpdateToDoDto updateToDoDto) {
        ToDo existingToDo = toDoRepo.findById(id)
                .orElseThrow(() -> new ToDoNotFoundException(id));

        ToDo updated = new ToDo(
                existingToDo.id(),
                updateToDoDto.description(),
                updateToDoDto.status()
        );

        return toDoRepo.save(updated);
    }

    public void deleteTodo(String id) {
        toDoRepo.deleteById(id);
    }
}
