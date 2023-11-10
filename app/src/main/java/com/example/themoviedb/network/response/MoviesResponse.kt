package com.example.themoviedb.network.response

import com.example.themoviedb.model.Movie

data class MoviesResponse (
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)