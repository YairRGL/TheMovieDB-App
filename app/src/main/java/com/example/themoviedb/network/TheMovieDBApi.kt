package com.example.themoviedb.network

import com.example.themoviedb.network.response.MoviesResponse
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface TheMovieDBApi {
    @GET(value = "popular")
    suspend fun getPopularMovies(): MoviesResponse

    @GET(value = "now_playing")
    suspend fun getNowPlayingMovies(): MoviesResponse
}