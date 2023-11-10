package com.example.themoviedb.utils

import com.example.themoviedb.BuildConfig

class Constants {
    companion object {
        val TMDBAPI_BASE_URL = "https://api.themoviedb.org/3/movie/"
        val API_KEY =  BuildConfig.TMDB_API_KEY
        val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    }
}
