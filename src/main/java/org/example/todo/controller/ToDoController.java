package org.example.todo.controller;

import org.example.todo.dto.CreateToDoDto;
import org.example.todo.model.ToDo;
import org.example.todo.service.ToDoService;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ToDo findById(@PathVariable String id) {
        return toDoService.findById(id);
    }

    @PostMapping
    public ToDo addToDo(@RequestBody CreateToDoDto createToDoDto) {
        return toDoService.addTodo(createToDoDto);
    }

    @PutMapping("/{id}")
    public ToDo updateToDo(@PathVariable String id, @RequestBody ToDo toDo) {
        return toDoService.updateTodo(toDo);
    }

    @DeleteMapping("/{id}")
    public void deleteToDo(@PathVariable String id) {
        toDoService.deleteTodo(id);
    }
}
