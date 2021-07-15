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
    NavHost(navController = navController, startDestination = "screenOne") {
        val details=

        composable("screenOne") {
            MovieLazyList ( navigation = {
                navController.navigate("screenTwo/038029"){}
            })

        }
        composable("screenTwo/{userId}", arguments = listOf(navArgument("userId") { type = NavType.StringType })) {
            DetailsMovie ( "${it.arguments?.get("userId")}")
        }

    }
}