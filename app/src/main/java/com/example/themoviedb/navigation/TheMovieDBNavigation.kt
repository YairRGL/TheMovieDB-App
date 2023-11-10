package com.example.themoviedb.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.themoviedb.screens.main.MainScreen
import com.example.themoviedb.screens.main.MainViewModel
import com.example.themoviedb.screens.splash.SplashScreen
import com.example.themoviedb.screens.user.UserScreen
import com.example.themoviedb.screens.user.UserViewModel

@Composable
fun TheMovieDBNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = TheMovieDBScreens.SplashScreen.name){
        composable(TheMovieDBScreens.SplashScreen.name){
            SplashScreen(navController = navController)
        }

        composable(TheMovieDBScreens.MainScreen.name){
            val mainViewModel = hiltViewModel<MainViewModel>()
            MainScreen(navController = navController, mainViewModel)
        }

        composable(TheMovieDBScreens.UserScreen.name){
            val userViewModel = hiltViewModel<UserViewModel>()
            UserScreen(navController = navController, userViewModel)
        }
    }
}