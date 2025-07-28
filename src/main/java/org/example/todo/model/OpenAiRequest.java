package org.example.todo.model;

import java.util.List;

public record OpenAiRequest(String model,
                            List<OpenAiMessages> messages) {
}

