package com.example.themoviedb.repository

import com.example.themoviedb.data.MovieDao
import com.example.themoviedb.model.Movie
import com.example.themoviedb.model.MovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDbRepository @Inject constructor(private val movieDao: MovieDao){

    fun getNowPlayingMovies(): Flow<List<MovieEntity>> = movieDao.getMovies()

    suspend fun insertMovieList(movies: List<MovieEntity>) = movieDao.insertMovieList(movies)

    suspend fun updateMovie(movie: MovieEntity) = movieDao.updateMovie(movie)

}