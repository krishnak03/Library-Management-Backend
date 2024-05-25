package com.example.library.management.tool.library.dao;

import com.example.library.management.tool.library.dto.language.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LanguageDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Language> getAllLanguages() {
        String getAllLanguageQuery = "SELECT * FROM language;";
        return jdbcTemplate.query(getAllLanguageQuery, new LanguageDao.LanguageRowMapper());
    }

    private static final class LanguageRowMapper implements RowMapper<Language> {
        @Override
        public Language mapRow(ResultSet rs, int rowNum) throws SQLException {
            Language language = new Language();
            language.setLanguageId(rs.getInt("language_id"));
            language.setLanguageName(rs.getString("language_name"));
            return language;
        }
    }
}
