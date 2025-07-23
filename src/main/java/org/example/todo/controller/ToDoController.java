package org.example.todo.controller;

import org.example.todo.model.ToDo;
import org.example.todo.service.ToDoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class ToDoController {

    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping()
    public List<ToDo> findAllToDos() {
        return toDoService.findAll();
    }
}
