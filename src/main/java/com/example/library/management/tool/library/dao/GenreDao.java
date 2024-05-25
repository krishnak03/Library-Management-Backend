package com.example.library.management.tool.library.dao;

import com.example.library.management.tool.library.dto.genre.Genre;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
public class GenreDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Genre> getAllGenres() {
        String getAllGenresQuery = "SELECT * FROM genre;";
        try {
            return jdbcTemplate.query(getAllGenresQuery, new GenreRowMapper());
        } catch (Exception e) {
            System.out.println("Error retrieving genres: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private static final class GenreRowMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            Genre genre = new Genre();
            genre.setGenreId(rs.getInt("genre_id"));
            genre.setGenreName(rs.getString("genre_name"));
            return genre;
        }
    }

    public ApiResponse addGenre(String genreName) {
        String addGenreQuery = "INSERT INTO genre (genre_name) VALUES (?);";
        try {
            int rowsAffected = jdbcTemplate.update(addGenreQuery, genreName);
            if (rowsAffected > 0) {
                return new ApiResponse(true, "Genre added successfully.");
            } else {
                return new ApiResponse(false, "Error while adding Genre.");
            }
        } catch (Exception e) {
            return new ApiResponse(false, "Error adding genre: " + e.getMessage());
        }
    }

    public ApiResponse updateGenre(Genre genre) {
        String updateGenreQuery = "UPDATE genre SET genre_name = ? WHERE genre_id = ?;";
        try {
            int rowsAffected = jdbcTemplate.update(updateGenreQuery, genre.getGenreName(), genre.getGenreId());
            if (rowsAffected > 0) {
                return new ApiResponse(true, "Genre updated successfully.");
            } else {
                return new ApiResponse(false, "No genre found with ID: " + genre.getGenreId());
            }
        } catch (Exception e) {
            return new ApiResponse(false, "Error updating genre: " + e.getMessage());
        }
    }

    public ApiResponse deleteGenre(int genreId) {
        String deleteGenreQuery = "DELETE FROM genre WHERE genre_id = ?;";
        try {
            int rowsAffected = jdbcTemplate.update(deleteGenreQuery, genreId);
            if (rowsAffected > 0) {
                return new ApiResponse(true, "Genre deleted successfully.");
            } else {
                return new ApiResponse(false, "No genre found with ID: " + genreId);
            }
        } catch (Exception e) {
            return new ApiResponse(false, "Error deleting genre: " + e.getMessage());
        }
    }
}
