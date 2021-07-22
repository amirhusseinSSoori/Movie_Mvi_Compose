package com.example.movie_mvi_compose.ui.Navigation

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
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
import com.example.movie_mvi_compose.ui.intro.Splash
import com.example.movie_mvi_compose.ui.movie.MovieContract
import com.example.movie_mvi_compose.ui.movie.MovieLazyList
import com.example.movie_mvi_compose.ui.movie.MovieViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun InitialNavGraph() {

    val navController: NavHostController = rememberNavController()
    val scope = rememberCoroutineScope()
    NavHost(navController = navController, startDestination = NavScreen.Intro.route) {

        composable(NavScreen.Intro.route) {
            Splash()
            scope.launch {
                delay(5000)
                navController.navigate(NavScreen.Movie.route) {
                    popUpTo(NavScreen.Intro.route)
                    popUpTo(NavScreen.Intro.route) { inclusive = true }
                }
            }

        }

        composable(NavScreen.Movie.route) { backStackEntry ->
            val viewModel = hiltViewModel<MovieViewModel>(backStackEntry)
            viewModel.setEvent(MovieContract.Event.ShowMovie)
            MovieLazyList(navigateToDetailsScreen = {
                navController.navigate("${NavScreen.Details.route}/$it")
            }, viewModel = viewModel)
        }




        composable(
            routeWithArgument,
            arguments = listOf(navArgument(argument0) { type = NavType.StringType })
        ) {
            DetailsMovie("${it.arguments?.get(argument0)}")
        }
    }
}


sealed class NavScreen(val route: String) {

    object Movie : NavScreen("ScreenMovie")
    object Intro : NavScreen("ScreenIntro")
    object Details : NavScreen("ScreenDetails") {

        const val routeWithArgument: String = "ScreenDetails/{details}"

        const val argument0: String = "details"
    }
}
