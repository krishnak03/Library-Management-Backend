package com.example.library.management.tool.library.dao;

import com.example.library.management.tool.library.dto.language.Language;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import com.example.library.management.tool.library.exceptions.CustomLibraryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
public class LanguageDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final class LanguageRowMapper implements RowMapper<Language> {
        @Override
        public Language mapRow(ResultSet rs, int rowNum) throws SQLException {
            Language language = new Language();
            language.setLanguageId(rs.getInt("language_id"));
            language.setLanguageName(rs.getString("language_name"));
            return language;
        }
    }

    public ResponseEntity<?> getAllLanguages() {
        String getAllLanguageQuery = "SELECT * FROM \"language\";";
        try {
            List<Language> languages = jdbcTemplate.query(getAllLanguageQuery, new LanguageRowMapper());
            if (languages.isEmpty()) {
                return new ResponseEntity<>(new ApiResponse(false, "No languages found."), HttpStatus.OK);
            }
            return new ResponseEntity<>(languages, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "Exception occurred while retrieving all languages: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ApiResponse addLanguage(String languageName) {
        String addLanguageQuery = "INSERT INTO \"language\" (language_name) VALUES (?);";
        try {
            int rowsAffected = jdbcTemplate.update(addLanguageQuery, languageName);
            if (rowsAffected > 0) {
                return new ApiResponse(true, "Language added successfully.");
            } else {
                return new ApiResponse(false, "Error while adding Language.");
            }
        } catch (Exception e) {
            return new ApiResponse(false, "Error adding language: " + e.getMessage());
        }
    }

    public ApiResponse updateLanguage(Language language) {
        String updateLanguageQuery = "UPDATE \"language\" SET language_name = ? WHERE language_id = ?;";
        try {
            int rowsAffected = jdbcTemplate.update(updateLanguageQuery, language.getLanguageName(), language.getLanguageId());
            if (rowsAffected > 0) {
                return new ApiResponse(true, "Language updated successfully.");
            } else {
                return new ApiResponse(false, "No language found with ID: " + language.getLanguageId());
            }
        } catch (Exception e) {
            return new ApiResponse(false, "Error updating language: " + e.getMessage());
        }
    }

    public ApiResponse deleteLanguage(int languageId) {
        String deleteLanguageQuery = "DELETE FROM \"language\" WHERE language_id = ?;";
        try {
            int rowsAffected = jdbcTemplate.update(deleteLanguageQuery, languageId);
            if (rowsAffected > 0) {
                return new ApiResponse(true, "Language deleted successfully.");
            } else {
                return new ApiResponse(false, "No language found with ID: " + languageId);
            }
        } catch (Exception e) {
            return new ApiResponse(false, "Error deleting language: " + e.getMessage());
        }
    }

}
