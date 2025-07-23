package org.example.todo.controller;

import org.example.todo.model.ToDo;
import org.example.todo.service.ChangeLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class ChangeLogController {

    private final ChangeLogService changeLogService;

    public ChangeLogController(ChangeLogService changeLogService) {
        this.changeLogService = changeLogService;
    }

    @GetMapping("/undo")
    public ResponseEntity<ToDo> undo() {
        ToDo result = changeLogService.undoLast();
        if(result == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/redo")
    public ResponseEntity<ToDo> redo() {
        ToDo result = changeLogService.redoLast();
        if(result == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }
}
