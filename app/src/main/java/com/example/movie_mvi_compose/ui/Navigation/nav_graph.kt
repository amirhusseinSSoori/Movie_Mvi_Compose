package com.example.movie_mvi_compose.ui.Navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.movie_mvi_compose.ui.details.DetailsMovie
import com.example.movie_mvi_compose.ui.movie.MovieLazyList

@ExperimentalFoundationApi
@Composable
fun InitialNavGraph(){
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = "ScreenMovie") {
       

        composable("ScreenMovie") {
            MovieLazyList (navController)
        }
        composable("ScreenDetails/{details}", arguments = listOf(navArgument("details") { type = NavType.StringType })) {
            DetailsMovie ( "${it.arguments?.get("details")}")
        }
    }
}