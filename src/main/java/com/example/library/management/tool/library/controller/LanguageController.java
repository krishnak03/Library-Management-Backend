package com.example.library.management.tool.library.controller;


import com.example.library.management.tool.library.dto.language.Language;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import com.example.library.management.tool.library.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/languages")
public class LanguageController {

    @Autowired
    public LanguageService languageService;

    @GetMapping
    public ResponseEntity<?> getAllLanguages() {
        return languageService.getAllLanguages();
    }

    @PostMapping
    public ApiResponse addLanguage(@RequestBody Language language) {
        return languageService.addLanguage(language.getLanguageName());
    }

    @PutMapping
    public ApiResponse updateLanguage(@RequestBody Language language){
        return languageService.updateLanguage(language);
    }

    @DeleteMapping
    public ApiResponse deleteLanguage(@RequestBody Language language) {
        return languageService.deleteLanguage(language.getLanguageId());
    }

}
