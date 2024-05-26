package com.example.library.management.tool.library.service;

import com.example.library.management.tool.library.dao.LanguageDao;
import com.example.library.management.tool.library.dto.language.Language;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import com.example.library.management.tool.library.util.Validator;
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
        if (Validator.isEmptyOrNull(languageName)) {
            return new ApiResponse(false, "Language name shouldn't be empty or null.");
        }
        return languageDao.addLanguage(languageName);
    }

    public ApiResponse updateLanguage(Language language) {
        if (Validator.isEmptyOrNull(language.getLanguageName())) {
            return new ApiResponse(false, "Genre name shouldn't be empty or null.");
        }
        return languageDao.updateLanguage(language);
    }

    public ApiResponse deleteLanguage(int languageId){
        if (languageId == 0) {
            return new ApiResponse(false, "Admin id shouldn't or null.");
        }
        return languageDao.deleteLanguage(languageId);
    }
}
