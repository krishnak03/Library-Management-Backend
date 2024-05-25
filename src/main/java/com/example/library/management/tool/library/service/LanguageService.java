package com.example.library.management.tool.library.service;

import com.example.library.management.tool.library.dao.LanguageDao;
import com.example.library.management.tool.library.dto.genre.Genre;
import com.example.library.management.tool.library.dto.language.Language;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {

    @Autowired
    public LanguageDao languageDao;

    public List<Language> getAllLanguages(){
        return languageDao.getAllLanguages();
    }

    public ApiResponse addLanguage(String languageName) {
        return languageDao.addLanguage(languageName);
    }

    public ApiResponse updateLanguage(Language language) {
        return languageDao.updateLanguage(language);
    }

    public ApiResponse deleteLanguage(int languageId){
        return languageDao.deleteLanguage(languageId);
    }
}
