package com.example.library.management.tool.library.service;

import com.example.library.management.tool.library.dao.LanguageDao;
import com.example.library.management.tool.library.dto.language.Language;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import com.example.library.management.tool.library.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {

    @Autowired
    public LanguageDao languageDao;

    public ResponseEntity<?> getAllLanguages() {
        return languageDao.getAllLanguages();
    }

    public ApiResponse addLanguage(String languageName) {
        if (ValidatorUtil.isEmptyOrNull(languageName)) {
            return new ApiResponse(false, "Language name shouldn't be empty or null.");
        }
        return languageDao.addLanguage(languageName);
    }

    public ApiResponse updateLanguage(Language language) {
        if (language.getLanguageId() == null) {
            return new ApiResponse(false, "Language id shouldn't or null.");
        }
        if (ValidatorUtil.isEmptyOrNull(language.getLanguageName())) {
            return new ApiResponse(false, "LanguageId and Language name shouldn't be empty or null.");
        }
        return languageDao.updateLanguage(language);
    }

    public ApiResponse deleteLanguage(Integer languageId){
        if (languageId == null) {
            return new ApiResponse(false, "Language id shouldn't or null.");
        }
        return languageDao.deleteLanguage(languageId);
    }
}
