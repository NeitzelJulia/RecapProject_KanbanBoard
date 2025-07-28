package org.example.todo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.todo.model.OpenAiMessages;
import org.example.todo.model.OpenAiRequest;
import org.example.todo.model.OpenAiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
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

    public String checkSpelling(String input) {

        String prompt = String.format("""
        Check the spelling of this String: "%s"
        Answer with a JSON object like this:

        {
           "original": "Ich habe heut kein Brot gekuft.",
           "corrected": "Ich habe heute kein Brot gekauft.",
           "corrections": [
             {
               "originalWord": "heut",
               "correctedWord": "heute",
               "position": 3
             },
             {
               "originalWord": "gekuft",
               "correctedWord": "gekauft",
               "position": 6
             }
           ]
         }
        """, input);

        try {
            String aiResponse = sendMessage(prompt);
            ObjectMapper mapper = new ObjectMapper();
            var jsonMap = mapper.readValue(aiResponse, Map.class);
            return (String) jsonMap.get("corrected");
        } catch (Exception e) {
            return input;
        }
    }
}
