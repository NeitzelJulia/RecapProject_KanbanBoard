package org.example.todo.controller;

import org.example.todo.model.ToDo;
import org.example.todo.service.UndoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/undo")
public class UndoController {

    private final UndoService undoService;

    public UndoController(UndoService undoService) {
        this.undoService = undoService;
    }

    @GetMapping
    public ResponseEntity<ToDo> undo() {
        ToDo result = undoService.undoLast();
        if(result == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }
}
