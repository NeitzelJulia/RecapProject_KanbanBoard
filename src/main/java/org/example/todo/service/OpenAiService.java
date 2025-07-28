package org.example.todo.service;

import org.example.todo.model.OpenAiMessages;
import org.example.todo.model.OpenAiRequest;
import org.example.todo.model.OpenAiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.util.List;

public class OpenAiService {

    private final RestClient restClient;
    private final static String BASE_URL = "https://api.openai.com/v1/chat/completions";

    public OpenAiService(RestClient.Builder restClientBuilder, @Value("${OPENAI_KEY}") String apikey) {
        this.restClient = restClientBuilder
                .baseUrl(BASE_URL)
                .defaultHeader("Authorization", "Bearer " + apikey)
                .build();
    }

    public String sendMessage(String userInput) {
        OpenAiRequest request = new OpenAiRequest(
                "gpt-4o-mini",
                List.of(new OpenAiMessages("user", userInput))
        );

        ResponseEntity<OpenAiResponse> response = restClient
                .post()
                .body(request)
                .retrieve()
                .toEntity(OpenAiResponse.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody().choices().getFirst().message().content();
        } else {
            throw new RuntimeException("Fehler beim Abrufen der Antwort von OpenAI");
        }
    }
}
