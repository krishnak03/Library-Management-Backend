package com.example.library.management.tool.library.service;

import com.example.library.management.tool.library.dao.LanguageDao;
import com.example.library.management.tool.library.dto.language.Language;
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
}
