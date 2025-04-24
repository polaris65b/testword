package com.example.usercrud.keyword.controller;

import com.example.usercrud.keyword.dto.KeywordRequest;
import com.example.usercrud.keyword.dto.KeywordResponse;
import com.example.usercrud.keyword.service.KeywordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/keywords")
public class KeywordController {
    @Autowired
    private KeywordService keywordService;

    @PostMapping("/suggest")
    public ResponseEntity<KeywordResponse> suggestKeywords(@Valid @RequestBody KeywordRequest request) {
        return ResponseEntity.ok(keywordService.suggestKeywords(request.getSearchTerm()));
    }
} 