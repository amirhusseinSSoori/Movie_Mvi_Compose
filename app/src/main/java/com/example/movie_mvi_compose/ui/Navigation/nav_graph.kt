package com.example.movie_mvi_compose.ui.Navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.movie_mvi_compose.ui.Navigation.NavScreen.Details.argument0
import com.example.movie_mvi_compose.ui.Navigation.NavScreen.Details.routeWithArgument
import com.example.movie_mvi_compose.ui.details.DetailsMovie
import com.example.movie_mvi_compose.ui.movie.MovieContract
import com.example.movie_mvi_compose.ui.movie.MovieLazyList
import com.example.movie_mvi_compose.ui.movie.MovieViewModel

@ExperimentalFoundationApi
@Composable
fun InitialNavGraph(){

    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = NavScreen.Movie.route) {
       

        composable( NavScreen.Movie.route) {backStackEntry ->
            val viewModel = hiltViewModel<MovieViewModel>(backStackEntry)
            viewModel.setEvent(MovieContract.Event.ShowMovie)
            MovieLazyList (navController,viewModel)
        }


        composable(routeWithArgument, arguments = listOf(navArgument(argument0) { type = NavType.StringType })) {
            DetailsMovie ( "${it.arguments?.get(NavScreen.Details.argument0)}")
        }
    }
}




sealed class NavScreen(val route: String) {

    object Movie : NavScreen("ScreenMovie")

    object Details : NavScreen("ScreenDetails") {

        const val routeWithArgument: String = "ScreenDetails/{details}"

        const val argument0: String = "details"
    }
}
