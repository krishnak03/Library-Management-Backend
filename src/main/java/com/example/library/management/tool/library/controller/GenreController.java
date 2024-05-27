package com.example.library.management.tool.library.controller;


import com.example.library.management.tool.library.dto.genre.Genre;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import com.example.library.management.tool.library.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/genres")
public class GenreController {

    @Autowired
    public GenreService genreService;

    @GetMapping
    public ResponseEntity<?> getAllGenres() {
        return genreService.getAllGenres();
    }

    @PostMapping
    public ApiResponse addGenre(@RequestBody Genre genre) {
        return genreService.addGenre(genre.getGenreName());
    }

    @PutMapping
    public ApiResponse updateGenre(@RequestBody Genre genre) {
        return genreService.updateGenre(genre);
    }

    @DeleteMapping
    public ApiResponse deleteGenre(@RequestBody Genre genre) {
        return genreService.deleteGenres(genre.getGenreId());
    }
}
