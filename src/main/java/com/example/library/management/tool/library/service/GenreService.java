package com.example.library.management.tool.library.service;

import com.example.library.management.tool.library.dao.GenreDao;
import com.example.library.management.tool.library.dto.genre.Genre;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    @Autowired
    public GenreDao genreDao;

    public List<Genre> getAllGenres() {
        return genreDao.getAllGenres();
    }

    public ApiResponse addGenre(String genreName) {
        return genreDao.addGenre(genreName);
    }

    public ApiResponse updateGenre(Genre genre) {
        return genreDao.updateGenre(genre);
    }

    public ApiResponse deleteGenres(int genreId){
        return genreDao.deleteGenre(genreId);
    }
}
