package org.example.todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoChangeLogEntryException extends RuntimeException {
    public NoChangeLogEntryException() {
        super("No Operation found");
    }
}
