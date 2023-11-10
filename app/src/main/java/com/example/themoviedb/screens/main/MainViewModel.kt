package com.example.themoviedb.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.data.DataOrException
import com.example.themoviedb.model.MovieEntity
import com.example.themoviedb.network.response.MoviesResponse
import com.example.themoviedb.repository.MovieDbRepository
import com.example.themoviedb.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MovieRepository,): ViewModel(){
    private val _movieList = MutableStateFlow<List<MovieEntity>>(emptyList())
    val movieList = _movieList.asStateFlow()

/*    init {
        viewModelScope.launch(Dispatchers.IO){
            repository.getNowPlayingMovies().distinctUntilChanged()
                .collect { listOfMovies ->
                    if(listOfMovies.isNullOrEmpty()){
                        val response =  repositoryMovie.getNowPlayingMovies()
                        val moviesDB: List<MovieEntity>? = response.data?.results?.map { movie ->
                            MovieEntity(
                                id = movie.idval,
                                overview = movie.overview,
                                posterPath = movie.poster_path,
                                releaseDate = movie.release_date,
                                title = movie.title,
                                voteAverage = movie.vote_average
                            )
                        }
                        repository.insertMovieList(moviesDB!!)
                    } else {
                        _movieList.value = listOfMovies
                    }

                }
        }
    }*/

    suspend fun getPopularMovies(): DataOrException<MoviesResponse, Boolean, Exception>{
        return repository.getPopularMovies()
    }

    suspend fun getNowPlayingMovies(): DataOrException<MoviesResponse, Boolean, Exception>{
        return repository.getNowPlayingMovies()
    }
}