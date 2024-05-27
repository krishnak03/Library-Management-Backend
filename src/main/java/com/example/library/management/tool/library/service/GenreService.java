package com.example.library.management.tool.library.service;

import com.example.library.management.tool.library.dao.GenreDao;
import com.example.library.management.tool.library.dto.genre.Genre;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;

import com.example.library.management.tool.library.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    @Autowired
    public GenreDao genreDao;
    
    public ResponseEntity<?> getAllGenres() {
        return genreDao.getAllGenres();
    }

    public ApiResponse addGenre(String genreName) {
        if (ValidatorUtil.isEmptyOrNull(genreName)) {
            return new ApiResponse(false, "Genre name shouldn't be empty or null.");
        }
        return genreDao.addGenre(genreName);
    }

    public ApiResponse updateGenre(Genre genre) {
        if (genre.getGenreId() == null) {
            return new ApiResponse(false, "Genre Id shouldn't be null.");
        }
        if (ValidatorUtil.isEmptyOrNull(genre.getGenreName())) {
            return new ApiResponse(false, "Genre name shouldn't be empty or null.");
        }
        return genreDao.updateGenre(genre);
    }

    public ApiResponse deleteGenres(Integer genreId) {
        if (genreId == null) {
            return new ApiResponse(false, "Genre Id shouldn't be null.");
        }
        return genreDao.deleteGenre(genreId);
    }
}
