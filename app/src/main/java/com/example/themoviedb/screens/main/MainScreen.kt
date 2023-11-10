package com.example.themoviedb.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.themoviedb.data.DataOrException
import com.example.themoviedb.model.Movie
import com.example.themoviedb.network.response.MoviesResponse
import com.example.themoviedb.widgets.MovieCard
import com.example.themoviedb.widgets.PopularMovieItem
import com.example.themoviedb.widgets.TheMovieDBAppBar
import com.example.themoviedb.widgets.TheMovieDBBottomBar

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = hiltViewModel()) {
    val popularMoviesData =
        produceState<DataOrException<MoviesResponse, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) {
            value = mainViewModel.getNowPlayingMovies()
        }.value

    if (popularMoviesData.loading == true) {
        Column(
            modifier = Modifier.fillMaxSize().background(Color(0xFF000000)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            CircularProgressIndicator()

        }
    } else if (popularMoviesData.data != null) {
        MainScaffold(popularMovies = popularMoviesData.data!!, navController = navController)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(popularMovies: MoviesResponse, navController: NavController) {
    Scaffold(
        topBar = {
            TheMovieDBAppBar(title = "MOVIES", navController = navController)
        },
        bottomBar = {
            TheMovieDBBottomBar(navController = navController, selectedItemIndex = 0)
        }, containerColor = Color(0xFF000000)
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainContent(data = popularMovies.results, navController)
        }
    }
}

@Composable
fun MainContent(data: List<Movie>, navController: NavController) {
    var selectedMovie by remember { mutableStateOf(data.first()) }

    MovieCard(movie = selectedMovie)
    Divider(
        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
        thickness = 2.dp,
        color = Color.White
    )
    Text(
        text = "Now Playing",
        color = Color.White,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.bodyMedium
    )
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(data) { movie ->
            PopularMovieItem(movie = movie) {
                selectedMovie = it
            }
        }
    }
}




