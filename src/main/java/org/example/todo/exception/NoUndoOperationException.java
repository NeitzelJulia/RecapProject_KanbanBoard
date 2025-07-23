package org.example.todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoUndoOperationException extends RuntimeException {
    public NoUndoOperationException() {
        super("No Operation found");
    }
}
