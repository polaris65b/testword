package com.example.usercrud.keyword.service;

import com.example.usercrud.keyword.dto.KeywordResponse;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeywordService {
    @Autowired
    private OpenAiService openAiService;

    public KeywordResponse suggestKeywords(String searchTerm) {
        String prompt = String.format(
            "Suggest 5 related search keywords for '%s'. " +
            "Return only the keywords separated by commas, no additional text.",
            searchTerm
        );

        CompletionRequest completionRequest = CompletionRequest.builder()
            .model("gpt-3.5-turbo-instruct")
            .prompt(prompt)
            .maxTokens(100)
            .temperature(0.7)
            .build();

        String response = openAiService.createCompletion(completionRequest)
            .getChoices()
            .get(0)
            .getText()
            .trim();

        List<String> keywords = Arrays.stream(response.split(","))
            .map(String::trim)
            .collect(Collectors.toList());

        KeywordResponse keywordResponse = new KeywordResponse();
        keywordResponse.setSuggestedKeywords(keywords);
        return keywordResponse;
    }
} 