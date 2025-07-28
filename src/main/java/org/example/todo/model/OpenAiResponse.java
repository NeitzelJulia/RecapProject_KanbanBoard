package org.example.todo.model;

import java.util.List;

public record OpenAiResponse(List<OpenAiChoice> choices) {
}
