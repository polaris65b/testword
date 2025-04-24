package com.example.usercrud.keyword.dto;

import lombok.Data;
import java.util.List;

@Data
public class KeywordResponse {
    private List<String> suggestedKeywords;
} 