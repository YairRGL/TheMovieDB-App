package com.example.themoviedb.repository

import com.example.themoviedb.data.DataOrException
import com.example.themoviedb.data.MovieDao
import com.example.themoviedb.model.MovieEntity
import com.example.themoviedb.network.TheMovieDBApi
import com.example.themoviedb.network.response.MoviesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepository @Inject constructor(private val api: TheMovieDBApi, private val movieDao: MovieDao){

    suspend fun getPopularMovies(): DataOrException<MoviesResponse, Boolean, Exception> {
        return try {
            withContext(Dispatchers.IO) {
                val response = api.getNowPlayingMovies()
                val moviesDB: List<MovieEntity> = response.results.map { movie ->
                    MovieEntity(
                        id = movie.idval,
                        overview = movie.overview,
                        posterPath = movie.poster_path,
                        releaseDate = movie.release_date,
                        title = movie.title,
                        voteAverage = movie.vote_average
                    )
                }
                movieDao.insertMovieList(moviesDB)

                DataOrException(data = response)
            }
        } catch (e: Exception) {
            DataOrException(e = e)
        }
    }

    suspend fun getNowPlayingMovies(): DataOrException<MoviesResponse, Boolean, Exception>{
        val response = try {
            api.getNowPlayingMovies()
        } catch (e: Exception){
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }

}