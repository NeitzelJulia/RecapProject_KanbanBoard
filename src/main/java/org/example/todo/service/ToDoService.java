package org.example.todo.service;

import org.example.todo.model.ToDo;
import org.example.todo.repository.ToDoRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoService {

    private final ToDoRepo toDoRepo;

    public ToDoService(ToDoRepo toDoRepo) {
        this.toDoRepo = toDoRepo;
    }

    public List<ToDo> findAll() {
        return toDoRepo.findAll();
    }
}
